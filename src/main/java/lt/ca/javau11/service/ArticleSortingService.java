package lt.ca.javau11.service;

import lt.ca.javau11.entity.Article;
import lt.ca.javau11.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleSortingService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticlesSortedBySource() {
        return articleRepository.findAll().stream()
                .sorted((a1, a2) -> a1.getSource().compareToIgnoreCase(a2.getSource()))
                .collect(Collectors.toList());
    }

    public List<Article> getArticlesBySectionSortedBySource(String section) {
        return articleRepository.findAll().stream()
                .filter(article -> article.getSection().equalsIgnoreCase(section))
                .sorted((a1, a2) -> a1.getSource().compareToIgnoreCase(a2.getSource()))
                .collect(Collectors.toList());
    }

    public List<Article> getAllArticlesSortedByTitle() {
        return articleRepository.findAll().stream()
                .sorted((a1, a2) -> a1.getTitle().compareToIgnoreCase(a2.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Article> getArticlesBySectionSortedByTitle(String section) {
        return articleRepository.findAll().stream()
                .filter(article -> article.getSection().equalsIgnoreCase(section))
                .sorted((a1, a2) -> a1.getTitle().compareToIgnoreCase(a2.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Article> getAllArticlesSortedByPublishedDate() {
        return articleRepository.findAll().stream()
                .sorted((a1, a2) -> a2.getPublishedDate().compareToIgnoreCase(a1.getPublishedDate()))
                .collect(Collectors.toList());
    }

    public List<Article> getArticlesBySectionSortedByPublishedDate(String section) {
        return articleRepository.findAll().stream()
                .filter(article -> article.getSection().equalsIgnoreCase(section))
                .sorted((a1, a2) -> a2.getPublishedDate().compareToIgnoreCase(a1.getPublishedDate()))
                .collect(Collectors.toList());
    }
}