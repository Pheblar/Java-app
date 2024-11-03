package by.kulevets.demociproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableRedisRepositories
public class DemoCiProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCiProjApplication.class, args);
    }


}
