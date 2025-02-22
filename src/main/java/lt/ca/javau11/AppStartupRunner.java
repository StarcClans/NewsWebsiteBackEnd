package lt.ca.javau11;

import lt.ca.javau11.service.ArticleCleanupService;
import lt.ca.javau11.service.ArticleFetcherService;
//import lt.ca.javau11.service.ArticleUpdateScheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    private ArticleFetcherService articleFetcherService;

    @Autowired
    private ArticleCleanupService articleCleanupService;

    @Override
    public void run(String... args) throws Exception {
        articleFetcherService.fetchArticles();
        articleCleanupService.removeDuplicatesLeavingOne();
        //ArticleUpdateScheduler.updateArticlesScheduled();
    }
}