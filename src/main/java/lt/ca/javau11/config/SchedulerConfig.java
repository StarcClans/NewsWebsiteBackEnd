package lt.ca.javau11.config;

import lt.ca.javau11.service.ArticleUpdateScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public ArticleUpdateScheduler articleUpdateScheduler() {
        return new ArticleUpdateScheduler();
    }
}