package by.kulevets.demociproj.service;

import by.kulevets.demociproj.entity.model.CachePostModel;
import by.kulevets.demociproj.entity.model.PostModel;
import by.kulevets.demociproj.entity.pojo.PostPojo;
import by.kulevets.demociproj.enumeration.Layer;
import by.kulevets.demociproj.enumeration.LogLevel;
import by.kulevets.demociproj.mapper.Mapper;
import by.kulevets.demociproj.repository.PostRepository;
import by.kulevets.demociproj.repository.RedisPostRepository;
import by.kulevets.demociproj.utils.FluentdUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentd.logger.FluentLogger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
@EnableScheduling
public class DefaultPostService implements PostService {
    private static final String POSTS_KEY = "posts";
    private FluentLogger LOG;
    private static final Logger log = LogManager.getLogger(DefaultPostService.class);
    private final PostRepository postRepository;
    private final RedisPostRepository redisPostRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Mapper mapper;
    private final Boolean fluentdEnabled;

    public DefaultPostService(@Value("${fluentd.host}") String fluentdHost, @Value("${fluentd.port}") String fluentdPort, PostRepository postRepository, RedisPostRepository redisPostRepository, RedisTemplate<String, Object> redisTemplate, Mapper mapper, @Value("${fluentd.enabled}") Boolean fluentdEnabled) {
        this.fluentdEnabled = fluentdEnabled;
        this.LOG = fluentdEnabled ? FluentLogger.getLogger(
                "[demo-ci-proj]",
                fluentdHost,
                Integer.parseInt(fluentdPort))
                : null;
        this.postRepository = postRepository;
        this.redisPostRepository = redisPostRepository;
        this.redisTemplate = redisTemplate;
        this.mapper = mapper;
    }

    @Override
    public void create(PostPojo pojo) {
        try {
            PostModel model = postRepository.save(mapper.toModel(pojo));
            Optional<CachePostModel> cached = redisPostRepository.findById(model.getId());
            if (cached.isEmpty()) {
                redisPostRepository.save(mapper.toCacheModel(model));
            }
            if (fluentdEnabled) {
                Map<String, Object> logmap = FluentdUtils.buildLog(
                        LogLevel.INFO,
                        Layer.SERVICE,
                        DefaultPostService.class.getName().concat("#create"),
                        "Pojo was saved",
                        model
                );
                LOG.log("#create", logmap);
            }
        } catch (IllegalStateException e) {
            if (fluentdEnabled) {
                Map<String, Object> logmap = FluentdUtils.buildLog(
                        LogLevel.ERROR,
                        Layer.SERVICE,
                        DefaultPostService.class.getName().concat("#create"),
                        e.getMessage(),
                        pojo
                );
                log.info("Collected errorMap: {}", logmap);
                LOG.log("#create", logmap);
            }
            log.error(e.getMessage());
        }

    }

    @Override
    public List<PostModel> getAll() {
        var cached = StreamSupport.stream(redisPostRepository.findAll().spliterator(), true)
                .toList();
        var dbPostCount = postRepository.count();

        if (((long) cached.size())  == dbPostCount){
            return cached
                    .stream()
                    .map(mapper::toModel)
                    .toList();
        }

        return postRepository.findAll()
                .parallelStream()
                .toList();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void syncDatabaseWithCache() {
        log.info("[PostService] Starting synchronization... ");
        var cached = StreamSupport.stream(redisPostRepository.findAll().spliterator(), true)
                .map(CachePostModel::getId)
                .toList();

        List<CachePostModel> cache = List.of();
        List<PostModel> missingModels;

        if (!cached.isEmpty()) {
            missingModels = postRepository.findByIds(cached);
        } else {
            missingModels = postRepository.findAll();
        }

        if (!missingModels.isEmpty()) {
            cache = missingModels
                    .stream()
                    .map(mapper::toCacheModel)
                    .toList();
        }

        redisPostRepository.saveAll(cache);

        log.info("[PostService] Ending synchronization... ");
    }
}
