package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.News;
import pe.edu.unap.oti.cms.service.NewsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class NewsController {
    
    @Autowired
    private NewsService newsService;
    
    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        try {
            List<News> news = newsService.findAll();
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        try {
            Optional<News> news = newsService.findById(id);
            if (news.isPresent()) {
                // Increment view count
                newsService.incrementViews(id);
                return ResponseEntity.ok(news.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/published")
    public ResponseEntity<List<News>> getPublishedNews() {
        try {
            List<News> news = newsService.findPublishedNews();
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<News>> getNewsByCategory(@PathVariable String category) {
        try {
            List<News> news = newsService.findNewsByCategory(category);
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<News>> searchNews(@RequestParam String title) {
        try {
            List<News> news = newsService.searchByTitle(title);
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        try {
            News savedNews = newsService.save(news);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News news) {
        try {
            News updatedNews = newsService.update(id, news);
            return ResponseEntity.ok(updatedNews);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        try {
            newsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}