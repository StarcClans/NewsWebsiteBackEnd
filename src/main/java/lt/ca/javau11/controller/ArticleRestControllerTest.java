package lt.ca.javau11.controller;

import lt.ca.javau11.entities.ArticleEntity;
import lt.ca.javau11.service.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getAllArticles_ShouldReturnListOfArticles() throws Exception {
        // Arrange
        ArticleEntity article1 = new ArticleEntity();
        article1.setUri("uri1");
        article1.setTitle("Article 1");

        ArticleEntity article2 = new ArticleEntity();
        article2.setUri("uri2");
        article2.setTitle("Article 2");

        List<ArticleEntity> articles = Arrays.asList(article1, article2);
        when(newsService.getAllArticles()).thenReturn(articles);

        // Act & Assert
        mockMvc.perform(get("/api/articles"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].uri").value("uri1"))
               .andExpect(jsonPath("$[0].title").value("Article 1"))
               .andExpect(jsonPath("$[1].uri").value("uri2"))
               .andExpect(jsonPath("$[1].title").value("Article 2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getArticleByUri_ShouldReturnArticle() throws Exception {
        // Arrange
        ArticleEntity article = new ArticleEntity();
        article.setUri("uri1");
        article.setTitle("Article 1");
        when(newsService.getArticleByUri("uri1")).thenReturn(article);

        // Act & Assert
        mockMvc.perform(get("/api/articles/uri1"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.uri").value("uri1"))
               .andExpect(jsonPath("$.title").value("Article 1"));
    }

     @Test
     @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteArticle_ShouldReturnNoContent() throws Exception {
        // Arrange
        String uriToDelete = "uri1";
        when(newsService.deleteArticle(uriToDelete)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/articles/" + uriToDelete))
               .andExpect(status().isNoContent());
          verify(newsService).deleteArticle(uriToDelete); // Verify that the deleteArticle method was called
    }

     @Test
      @WithMockUser(username = "admin", roles = "ADMIN")
    public void createArticle_ShouldReturnCreatedArticle() throws Exception {
        // Arrange
        String articleJson = "{\"uri\":\"newUri\",\"title\":\"New Article\"}";
        ArticleEntity newArticle = new ArticleEntity();
        newArticle.setUri("newUri");
        newArticle.setTitle("New Article");

        when(newsService.createArticle(any(ArticleEntity.class))).thenReturn(newArticle);

        // Act & Assert
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleJson))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.uri").value("newUri"))
               .andExpect(jsonPath("$.title").value("New Article"));
    }

      @Test
       @WithMockUser(username = "admin", roles = "ADMIN")
    public void updateArticle_ShouldReturnUpdatedArticle() throws Exception {
        // Arrange
        String uriToUpdate = "uri1";
        String updatedArticleJson = "{\"title\":\"Updated Article\"}";
        ArticleEntity updatedArticle = new ArticleEntity();
        updatedArticle.setUri(uriToUpdate);
        updatedArticle.setTitle("Updated Article");

        when(newsService.updateArticle(eq(uriToUpdate), any(ArticleEntity.class))).thenReturn(updatedArticle);

        // Act & Assert
        mockMvc.perform(put("/api/articles/" + uriToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedArticleJson))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.uri").value(uriToUpdate))
               .andExpect(jsonPath("$.title").value("Updated Article"));
    }

    @Test
     @WithMockUser(username = "admin", roles = "ADMIN")
    public void getArticlesBySection_ShouldReturnListOfArticlesInThatSection() throws Exception {
        // Arrange
        String sectionName = "Technology";
        ArticleEntity article1 = new ArticleEntity();
        article1.setUri("uri1");
        article1.setSection(sectionName);
        article1.setTitle("Tech Article 1");

        List<ArticleEntity> articles = Arrays.asList(article1);
        when(newsService.getArticleBySection(sectionName)).thenReturn(articles);

        // Act & Assert
        mockMvc.perform(get("/api/articles/section/" + sectionName))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].uri").value("uri1"))
               .andExpect(jsonPath("$[0].section").value(sectionName))
               .andExpect(jsonPath("$[0].title").value("Tech Article 1"));
    }

}