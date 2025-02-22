package lt.ca.javau11.controller;

import lt.ca.javau11.entity.FullArticleEntity;
import lt.ca.javau11.service.FullArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/fullarticles")
public class FullArticleController {

    @Autowired
    private FullArticleService fullArticleService;

    @GetMapping
    public List<FullArticleEntity> list() {
        return fullArticleService.getAllFullArticles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullArticleEntity> get(@PathVariable Long id) {
        try {
            FullArticleEntity fullArticle = fullArticleService.getFullArticleById(id).orElseThrow();
            return new ResponseEntity<>(fullArticle, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<FullArticleEntity> add(@RequestBody FullArticleEntity fullArticle) {
    	FullArticleEntity savedArticle = fullArticleService.saveFullArticle(fullArticle);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody FullArticleEntity fullArticle, @PathVariable Long id) {
        try {
            FullArticleEntity existFullArticle = fullArticleService.getFullArticleById(id).orElseThrow();
            fullArticle.setId(id);
            fullArticleService.saveFullArticle(fullArticle);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            fullArticleService.deleteFullArticle(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}