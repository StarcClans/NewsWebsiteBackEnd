package lt.ca.javau11.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.ca.javau11.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	Optional<Article> findByUri(String uri);
}