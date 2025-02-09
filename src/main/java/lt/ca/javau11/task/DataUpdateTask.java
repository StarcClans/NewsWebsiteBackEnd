package lt.ca.javau11.task;

import lt.ca.javau11.service.NewsService;
import lt.ca.javau11.service.SectionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataUpdateTask {
    private final SectionService sectionService;
    private final NewsService newsService;

    public DataUpdateTask(SectionService sectionService, NewsService newsService) {
        this.sectionService = sectionService;
        this.newsService = newsService;
    }
   @Scheduled(fixedRate = 600000)
   public void updateArticles(){
        sectionService.getListSections().forEach(
                sectionEntity -> newsService.fetchAndSaveArticles("all", sectionEntity.getSection())
        );
    }
}