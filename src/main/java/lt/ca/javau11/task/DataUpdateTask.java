package lt.ca.javau11.task;

import lt.ca.javau11.service.NewsService;
import lt.ca.javau11.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataUpdateTask {
    private static final Logger logger = LoggerFactory.getLogger(DataUpdateTask.class);
    private final SectionService sectionService;
    private final NewsService newsService;

    public DataUpdateTask(SectionService sectionService, NewsService newsService) {
        this.sectionService = sectionService;
        this.newsService = newsService;
    }

    @Scheduled(fixedRate = 600000)
    public void updateArticles() {
        try {
            logger.info("Starting article update task...");
            sectionService.getListSections().forEach(sectionEntity -> {
                try {
                    newsService.fetchAndSaveArticles("all", sectionEntity.getSection());
                } catch (Exception e) {
                    logger.error("Error fetching and saving articles for section {}: {}", sectionEntity.getSection(), e.getMessage(), e);
                }
            });
            logger.info("Article update task completed successfully.");
        } catch (Exception e) {
            logger.error("Error during article update task: {}", e.getMessage(), e);
        }
    }
}