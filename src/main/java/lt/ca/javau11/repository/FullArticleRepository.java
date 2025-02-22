package lt.ca.javau11.repository;
import lt.ca.javau11.entity.FullArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullArticleRepository extends JpaRepository<FullArticleEntity, Long> {
}