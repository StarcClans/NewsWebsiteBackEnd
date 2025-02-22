package lt.ca.javau11.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ca.javau11.entity.Article;
import lt.ca.javau11.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ArticleUpdateScheduler.class);

    @Autowired
    private ArticleFetcherService articleFetcherService;

    @Autowired
    private Environment env;

    @Scheduled(fixedRate = 3600000) // Every 10 minutes (600000 milliseconds)
    public void updateArticlesScheduled() throws Exception {
        logger.info("Scheduler is running");
        logger.info("SPRING_DATASOURCE_URL: " + env.getProperty("SPRING_DATASOURCE_URL"));
        logger.info("SPRING_DATASOURCE_USERNAME: " + env.getProperty("SPRING_DATASOURCE_USERNAME"));
        logger.info("SPRING_DATASOURCE_PASSWORD: " + env.getProperty("SPRING_DATASOURCE_PASSWORD"));
        logger.info("NYTIMES_API_KEY: " + env.getProperty("NYTIMES_API_KEY"));
        logger.info("APP_JWT_SECRET: " + env.getProperty("APP_JWT_SECRET"));
        logger.info("APP_JWT_EXPIRATION_MILLISECONDS: " + env.getProperty("APP_JWT_EXPIRATION_MILLISECONDS"));
        logger.info("Starting scheduled article update...");
        try {
            articleFetcherService.fetchArticles();
            logger.info("Scheduled article update completed successfully.");
        } catch (Exception e) {
            logger.error("Exception occurred during scheduled article update:", e);
            // Handle the exception appropriately (e.g., log, retry, etc.)
        }
    }
}