package lt.ca.javau11.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ca.javau11.entity.Article;
import lt.ca.javau11.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleFetcherService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleFetcherService.class);

    @Autowired
    private Environment env;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void fetchArticles() throws Exception {
        String apiKey = env.getProperty("nytimes.api.key");
        String sectionListUrl = "https://api.nytimes.com/svc/news/v3/content/section-list.json?api-key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String sectionListResponse = restTemplate.getForObject(sectionListUrl, String.class);

        logger.debug("Section list response: " + sectionListResponse);

        String articleUrl = "https://api.nytimes.com/svc/news/v3/content/all/all.json?api-key=" + apiKey + "&limit=500";
        String articleResponse = restTemplate.getForObject(articleUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(articleResponse);
        JsonNode results = root.get("results");

        if (results != null && results.isArray()) {
            List<Article> articlesToSave = new ArrayList<>();
            for (JsonNode result : results) {
                String uri = result.get("uri") != null ? result.get("uri").asText() : null;

                if (uri != null && !uri.isEmpty()) {
                    //  Check for existing article *before* creating a new one
                    Optional<Article> existingArticle = articleRepository.findByUri(uri);

                    if (existingArticle.isEmpty()) {
                        Article article = new Article();
                        try {
                            article.setUri(uri);
                            article.setSection(result.get("section") != null ? result.get("section").asText() : "");
                            article.setSubsection(result.get("subsection") != null ? result.get("subsection").asText() : "");
                            article.setTitle(result.get("title") != null ? result.get("title").asText() : "");
                            article.setAbstractText(result.get("abstract") != null ? result.get("abstract").asText() : "");
                            article.setUrl(result.get("url") != null ? result.get("url").asText() : "");
                            article.setShortUrl(result.get("short_url") != null ? result.get("short_url").asText() : "");

                            String byline = result.get("byline") != null ? result.get("byline").asText() : "";
                            int maxLength = 250;
                            if (byline.length() > maxLength) {
                                byline = byline.substring(0, maxLength) + "...";
                            }
                            article.setByline(byline);

                            if (result.get("thumbnail_standard") != null) {
                                article.setThumbnailStandard(result.get("thumbnail_standard").asText());
                            }
                            article.setItemType(result.get("item_type") != null ? result.get("item_type").asText() : "");
                            article.setSource(result.get("source") != null ? result.get("source").asText() : "");
                            article.setUpdatedDate(result.get("updated_date") != null ? result.get("updated_date").asText() : "");
                            article.setCreatedDate(result.get("created_date") != null ? result.get("created_date").asText() : "");
                            article.setPublishedDate(result.get("published_date") != null ? result.get("published_date").asText() : "");
                            article.setMaterialTypeFacet(result.get("material_type_facet") != null ? result.get("material_type_facet").asText() : "");
                            article.setKicker(result.get("kicker") != null ? result.get("kicker").asText() : "");
                            article.setHeadline(result.get("headline") != null ? result.get("headline").asText() : "");

                            JsonNode desFacetNode = result.get("des_facet");
                            String desFacet = (desFacetNode != null && desFacetNode.isArray()) ? String.join(", ", convertJsonArrayToList(desFacetNode)) : null;
                            article.setDesFacet(desFacet);

                            JsonNode orgFacetNode = result.get("org_facet");
                            String orgFacet = (orgFacetNode != null && orgFacetNode.isArray()) ? String.join(", ", convertJsonArrayToList(orgFacetNode)) : null;
                            article.setOrgFacet(orgFacet);

                            JsonNode perFacetNode = result.get("per_facet");
                            String perFacet = (perFacetNode != null && perFacetNode.isArray()) ? String.join(", ", convertJsonArrayToList(perFacetNode)) : null;
                            article.setPerFacet(perFacet);

                            JsonNode geoFacetNode = result.get("geo_facet");
                            String geoFacet = (geoFacetNode != null && geoFacetNode.isArray()) ? String.join(", ", convertJsonArrayToList(geoFacetNode)) : null;
                            article.setGeoFacet(geoFacet);

                            article.setSlugName(result.get("slug_name") != null ? result.get("slug_name").asText() : "");
                            article.setSource(result.get("source") != null ? result.get("source").asText() : "");
                            article.setFirstPublishedDate(result.get("first_published_date") != null ? result.get("first_published_date").asText() : "");
                            article.setSubheadline(result.get("subheadline") != null ? result.get("subheadline").asText() : "");

                            articlesToSave.add(article);
                        } catch (NullPointerException e) {
                            logger.error("Error processing article: " + result.toString());
                            e.printStackTrace();
                        }
                    } else {
                        logger.info("Skipping duplicate article with uri: " + uri);
                    }
                }
            }

            //  Save articles individually, handling exceptions for each
            for (Article article : articlesToSave) {
                try {
                    saveArticle(article);
                    logger.info("Successfully saved article with uri: " + article.getUri());
                } catch (DataIntegrityViolationException e) {
                    logger.warn("Skipping duplicate article with uri: " + article.getUri() + " due to unique constraint.");
                } catch (Exception e) {
                    logger.error("Error saving article with uri: " + article.getUri(), e);
                }
            }

        } else {
            System.out.println("No results found in the article response.");
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveArticle(Article article){
        articleRepository.save(article);
    }


    private List<String> convertJsonArrayToList(JsonNode jsonArray) {
        List<String> list = new ArrayList<>();
        for (JsonNode node : jsonArray) {
            list.add(node.asText());
        }
        return list;
    }
}