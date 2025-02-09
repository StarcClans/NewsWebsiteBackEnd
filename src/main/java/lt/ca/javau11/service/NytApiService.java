package lt.ca.javau11.service;

import lt.ca.javau11.models.Article;
import lt.ca.javau11.models.NytResponse;
import lt.ca.javau11.models.Section;
import lt.ca.javau11.util.ApiKeyReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NytApiService {

    private final WebClient webClient;
    private final ApiKeyReader apiKeyReader;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(NytApiService.class);

    @Value("${nyt.api.baseurl}")
    private String baseUrl;

    public NytApiService(WebClient.Builder webClientBuilder, ApiKeyReader apiKeyReader, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apiKeyReader = apiKeyReader;
        this.objectMapper = objectMapper;
         logger.info("Api key read from file: {}", apiKeyReader.getApiKey());
    }

    public List<Section> fetchSections() {
        String apiKey = apiKeyReader.getApiKey();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/section-list.json")
                .queryParam("api-key", apiKey);
        String uri = builder.build().toUriString();
        logger.info("Fetching sections from API with URL: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    logger.info("Response from section list received: {}", response);
                    try {
                        NytResponse<Section> sectionResponse = objectMapper.readValue(response, new com.fasterxml.jackson.core.type.TypeReference<NytResponse<Section>>(){});
                        logger.info("Parsed sections: {}", Objects.requireNonNullElse(sectionResponse.getResults(), new ArrayList<>()).size());
                        return Mono.just(sectionResponse.getResults());
                    } catch (Exception e) {
                        logger.error("Error while parsing the sections.", e);
                        return Mono.error(new RuntimeException("Error parsing section response", e));
                    }
                })
                .onErrorReturn(new ArrayList<>())
                .block();
    }


   public NytResponse<Article> fetchArticles(String source, String section) {
        String apiKey = apiKeyReader.getApiKey();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/{source}/{section}.json")
                .queryParam("api-key", apiKey);
        String uri = builder.build(source, section).toString();
         logger.info("Fetching articles from API with URL: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    logger.info("Response from article list received: {}", response);
                    try {
                        NytResponse<Article> articleResponse = objectMapper.readValue(response, new com.fasterxml.jackson.core.type.TypeReference<NytResponse<Article>>() {});
                        logger.info("Parsed articles: {}", Objects.requireNonNullElse(articleResponse.getResults(), new ArrayList<>()).size());
                        return Mono.just(articleResponse);
                    } catch (Exception e) {
                        logger.error("Error while parsing the articles.", e);
                        return Mono.error(new RuntimeException("Error parsing article response", e));
                    }
                })
                .onErrorReturn(new NytResponse<>())
                .block();
    }
}