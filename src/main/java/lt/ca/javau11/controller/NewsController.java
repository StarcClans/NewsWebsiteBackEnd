package lt.ca.javau11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ca.javau11.entities.ArticleEntity;
import lt.ca.javau11.entities.SectionEntity;
import lt.ca.javau11.service.NewsService;
import lt.ca.javau11.service.SectionService;
import lt.ca.javau11.util.ArticleArchiveFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NewsController {

    private final NewsService newsService;
    private final SectionService sectionService;
    private final ObjectMapper objectMapper;
    private final ArticleArchiveFilter articleArchiveFilter; // Inject the filter

    public NewsController(NewsService newsService, SectionService sectionService, ObjectMapper objectMapper, ArticleArchiveFilter articleArchiveFilter) {
        this.newsService = newsService;
        this.sectionService = sectionService;
        this.objectMapper = objectMapper;
        this.articleArchiveFilter = articleArchiveFilter;
    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Assuming the UserDetails implementation has a method to get the user ID
           // Implement UserDetails in your User entity and extract user ID
           // return ((CustomUserDetails) userDetails).getId();    
        }
        return null;
    }
    @GetMapping("/")
    public String index(Model model) {
        return "source";
    }

    @GetMapping("/sections")
    public String sections(@RequestParam String source, Model model) {
        System.out.println("Fetching sections for source " + source);
        List<SectionEntity> sections = sectionService.getListSections();
        System.out.println("Sections retrieved: " + sections.size());
        model.addAttribute("sections", sections);
        model.addAttribute("source", source);
        return "sections";
    }

    @GetMapping("/articles")
    public String articles(@RequestParam String source, @RequestParam String section, Model model) {
        newsService.fetchAndSaveArticles(source, section);
        List<ArticleEntity> articles = newsService.getArticleBySection(section).stream()
                .filter(article -> !articleArchiveFilter.isArchived(article)) // Filter out archived articles
                .collect(Collectors.toList());
        System.out.println("articles retrieved: " + articles.size());
        model.addAttribute("articles", articles);
        model.addAttribute("source", source);
        return "articles";
    }

    @GetMapping("/archived-articles")
    public String archivedArticles(@RequestParam String source, @RequestParam String section, Model model) {
        List<ArticleEntity> articles = newsService.getArticleBySection(section).stream()
                .filter(articleArchiveFilter::isArchived) // Filter for archived articles
                .collect(Collectors.toList());
        System.out.println("archived articles retrieved: " + articles.size());
        model.addAttribute("articles", articles);
        model.addAttribute("source", source);
        return "articles"; // Use the same "articles" view or create a separate one
    }
  //User Functionality starts here
  @PostMapping("/add-to-favorites")
    public String addArticleToFavorites(@RequestParam String uri, @RequestParam String source, Model model) {
      Long userId = getCurrentUserId();
        if (userId == null) {
            return "redirect:/login"; // Redirect to login if user is not authenticated
        }
        newsService.addArticleToFavorites(userId, uri);
        return "redirect:/articles?section=all&source=" + source;
    }

    @GetMapping("/favorites")
    public String viewFavorites(@RequestParam String source, Model model) {
      Long userId = getCurrentUserId();
        if (userId == null) {
            return "redirect:/login"; // Redirect to login if user is not authenticated
        }
        List<ArticleEntity> favoriteArticles = newsService.getFavoriteArticlesForUser(userId);
        model.addAttribute("articles", favoriteArticles);
        model.addAttribute("source", source);
        return "articles";
    }

    @GetMapping("/article-detail")
    public String articleView(@RequestParam String uri, @RequestParam String source, Model model) {
        ArticleEntity article = newsService.getArticleByUri(uri);
        System.out.println("Article with uri " + uri + " retrieved.");
        model.addAttribute("objectMapper", objectMapper);
        model.addAttribute("article", article);
        model.addAttribute("source", source);
        return "article-detail";
    }
}