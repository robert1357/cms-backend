package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unap.oti.cms.model.News;
import pe.edu.unap.oti.cms.repository.NewsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;
    
    public List<News> findAll() {
        return newsRepository.findAll();
    }
    
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }
    
    public List<News> findPublishedNews() {
        return newsRepository.findByPublishedTrueOrderByCreatedAtDesc();
    }
    
    public List<News> findNewsByCategory(String category) {
        return newsRepository.findByCategoryAndPublishedTrue(category);
    }
    
    public List<News> searchByTitle(String title) {
        return newsRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public News save(News news) {
        // Set publication date if being published for the first time
        if (news.getPublished() && news.getPublishedAt() == null) {
            news.setPublishedAt(LocalDateTime.now());
        }
        
        // Set default author if not provided
        if (news.getAuthorName() == null || news.getAuthorName().isEmpty()) {
            news.setAuthorName("Administrador");
        }
        
        // Set default authorId if not provided
        if (news.getAuthorId() == null) {
            news.setAuthorId(1L); // ID por defecto del administrador
        }
        
        return newsRepository.save(news);
    }
    
    public News update(Long id, News newsData) {
        News existingNews = newsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Noticia no encontrada con ID: " + id));
        
        // Update basic fields
        existingNews.setTitle(newsData.getTitle());
        existingNews.setContent(newsData.getContent());
        existingNews.setExcerpt(newsData.getExcerpt());
        existingNews.setImageUrl(newsData.getImageUrl());
        existingNews.setCategory(newsData.getCategory());
        existingNews.setTags(newsData.getTags());
        
        // Handle publication status
        if (newsData.getPublished() && !existingNews.getPublished()) {
            existingNews.setPublishedAt(LocalDateTime.now());
        }
        existingNews.setPublished(newsData.getPublished());
        
        return newsRepository.save(existingNews);
    }
    
    public void delete(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("Noticia no encontrada con ID: " + id);
        }
        newsRepository.deleteById(id);
    }
    
    public void incrementViews(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            news.setViews(news.getViews() + 1);
            newsRepository.save(news);
        }
    }
    
    public long getPublishedNewsCount() {
        return newsRepository.countByPublishedTrue();
    }
    
    public long getTotalNewsCount() {
        return newsRepository.count();
    }
}