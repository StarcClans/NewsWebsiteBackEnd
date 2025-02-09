package lt.ca.javau11.repositories;

import lt.ca.javau11.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity,String> {
    List<ArticleEntity> findByUserIdAndFavorite(Long userId, boolean favorite);
     Optional<ArticleEntity> findByUserIdAndUri(Long userId, String uri);
}