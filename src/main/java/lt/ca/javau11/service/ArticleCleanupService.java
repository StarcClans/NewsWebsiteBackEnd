package lt.ca.javau11.service;

import lt.ca.javau11.entity.Article;
import lt.ca.javau11.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCleanupService {

    @Autowired
    private ArticleRepository articleRepository;

    @PostConstruct
    public void removeDuplicatesLeavingOne() {
        List<Article> allArticles = articleRepository.findAll();
        List<String> uniqueUris = new ArrayList<>();
        List<Article> duplicatesToRemove = new ArrayList<>();

        for (Article article : allArticles) {
            if (uniqueUris.contains(article.getUri())) {
                duplicatesToRemove.add(article);
            } else {
                uniqueUris.add(article.getUri());
            }
        }

        // Remove all duplicates
        articleRepository.deleteAll(duplicatesToRemove);
        System.out.println("Removed " + duplicatesToRemove.size() + " duplicate articles, leaving one of each.");
    }
}