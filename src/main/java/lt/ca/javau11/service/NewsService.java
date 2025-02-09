package lt.ca.javau11.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ca.javau11.entities.ArticleEntity;
import lt.ca.javau11.exception.ResourceNotFoundException;
import lt.ca.javau11.models.Article;
import lt.ca.javau11.models.NytResponse;
import lt.ca.javau11.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NytApiService nytApiService;
    private final ArticleRepository articleRepository;
    private final ObjectMapper objectMapper;


    public NewsService(NytApiService nytApiService, ArticleRepository articleRepository, ObjectMapper objectMapper) {
        this.nytApiService = nytApiService;
        this.articleRepository = articleRepository;
        this.objectMapper = objectMapper;
    }

     public void fetchAndSaveArticles(String source, String section) {
        NytResponse<Article> articleResponse = nytApiService.fetchArticles(source, section);
        if (articleResponse != null && articleResponse.getResults() != null) {
            List<Article> articles = articleResponse.getResults();

            // Filter out articles that already exist in the database
            List<ArticleEntity> newArticleEntities = articles.stream()
                    .filter(article -> !articleRepository.existsById(article.getUri())) // Check if exists by URI
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());

            // Save the new articles
            if (!newArticleEntities.isEmpty()) {
                articleRepository.saveAll(newArticleEntities);
            }
        }
    }


    private ArticleEntity convertToEntity(Article article) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setUri(article.getUri());
        articleEntity.setSection(article.getSection());
        articleEntity.setSubsection(article.getSubsection());
        articleEntity.setTitle(article.getTitle());
        articleEntity.setAbstractText(article.getAbstractText());
        articleEntity.setUrl(article.getUrl());
        articleEntity.setShortUrl(article.getShort_url());
        articleEntity.setByline(article.getByline());
        articleEntity.setThumbnailStandard(article.getThumbnail_standard());
        articleEntity.setItemType(article.getItem_type());
        articleEntity.setSource(article.getSource());
        articleEntity.setUpdatedDate(parseDate(article.getUpdated_date()));
        articleEntity.setCreatedDate(parseDate(article.getCreated_date()));
        articleEntity.setPublishedDate(parseDate(article.getPublished_date()));
        articleEntity.setMaterialTypeFacet(article.getMaterial_type_facet());
        articleEntity.setKicker(article.getKicker());
        articleEntity.setHeadline(article.getHeadline());
        articleEntity.setDesFacet(article.getDes_facet() != null ? String.join(", ", article.getDes_facet()) : null);
        articleEntity.setOrgFacet(article.getOrg_facet() != null ? String.join(", ", article.getOrg_facet()) : null);
        articleEntity.setPerFacet(article.getPer_facet() != null ? String.join(", ", article.getPer_facet()) : null);
        articleEntity.setGeoFacet(article.getGeo_facet() != null ? String.join(", ", article.getGeo_facet()) : null);
        articleEntity.setBlogName(article.getBlog_name());
        articleEntity.setFavorite(false); // Set default value
        articleEntity.setUserId(0L);
        
        try {
            if (article.getRelated_urls() != null) {
                articleEntity.setRelatedUrls(objectMapper.writeValueAsString(article.getRelated_urls()));
            }
            if(article.getMultimedia() != null){
                articleEntity.setMultimedia(objectMapper.writeValueAsString(article.getMultimedia()));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error converting multimedia/related urls to JSON", e);
        }
        return articleEntity;
    }

    private LocalDateTime parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Handle parsing error
            return null; // or throw an exception, log the error, etc.
        }
    }

	// Implement methods to get a article by section:
    public List<ArticleEntity> getArticleBySection(String section) {
        return articleRepository.findAll().stream()
                .filter(articleEntity -> articleEntity.getSection().equalsIgnoreCase(section)).collect(Collectors.toList());
    }

    // Implement methods to get article by URI:
    public ArticleEntity getArticleByUri(String uri) {
        return articleRepository.findById(uri).orElse(null);
    }
  // Add Article to Favorites, UserID is foreign key
   public void addArticleToFavorites(Long userId, String articleUri) {
        ArticleEntity article = articleRepository.findById(articleUri).orElseThrow(() -> new ResourceNotFoundException("Article not found with uri: " + articleUri));
        article.setFavorite(true);
        article.setUserId(userId);
        articleRepository.save(article);
    }
    //Remove Article from favorites
    public void removeArticleFromFavorites(Long userId, String articleUri) {
          ArticleEntity article = articleRepository.findByUserIdAndUri(userId, articleUri).orElseThrow(() -> new ResourceNotFoundException("Article not found with uri: " + articleUri));
          articleRepository.delete(article);
    }
    // List of Article by user ID
    public List<ArticleEntity> getFavoriteArticlesForUser(Long userId) {
        return articleRepository.findByUserIdAndFavorite(userId, true);
    }
}