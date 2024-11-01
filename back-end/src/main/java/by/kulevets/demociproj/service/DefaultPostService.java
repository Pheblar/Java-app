package by.kulevets.demociproj.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Service
@RequestScope
public class DefaultPostService implements PostService {
    private static final String POSTS_KEY = "posts";
    private final FluentLogger LOG;
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
            log.info("Fluentd is connected to app: {}", LOG.isConnected());
            if (Objects.equals(pojo.getAuthor(), "Pepe")) {
                throw new IllegalStateException("Author cannot be Pepe!");
            }
            PostModel model = postRepository.save(mapper.toModel(pojo));
            PostModel cached = getById(model.getId());
            if (cached == null) {
                redisTemplate.opsForHash()
                        .put(POSTS_KEY, model.getId(), model);
                postRepository.save(model);
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
        Spliterator<PostModel> cachedSpliterator = redisPostRepository.findAll().spliterator();
        if (cachedSpliterator.estimateSize() > 0) {
            return StreamSupport.stream(cachedSpliterator, true)
                    .toList();
        }
        return postRepository.findAll();
    }

    @Override
    public PostModel getById(Long id) {
        return (PostModel) redisTemplate.opsForHash()
                .get(POSTS_KEY, id);
    }
}
