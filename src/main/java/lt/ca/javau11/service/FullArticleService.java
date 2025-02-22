package lt.ca.javau11.service;
import lt.ca.javau11.entity.FullArticleEntity;
import lt.ca.javau11.repository.FullArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FullArticleService {

    @Autowired
    private FullArticleRepository fullArticleRepository;

    public List<FullArticleEntity> getAllFullArticles() {
        return fullArticleRepository.findAll();
    }

    public Optional<FullArticleEntity> getFullArticleById(Long id) {
        return fullArticleRepository.findById(id);
    }

    public FullArticleEntity saveFullArticle(FullArticleEntity fullArticle) {
        return fullArticleRepository.save(fullArticle);
    }

    public void deleteFullArticle(Long id) {
        fullArticleRepository.deleteById(id);
    }
}