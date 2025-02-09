package lt.ca.javau11.util;

import lt.ca.javau11.entities.ArticleEntity;
import org.springframework.stereotype.Component;

@Component
public class ArticleArchiveFilter {

    public boolean isArchived(ArticleEntity article) {
        // Example: Archive articles with titles containing "Old News"
        return article.getTitle().toLowerCase().contains("old news");
    }
}