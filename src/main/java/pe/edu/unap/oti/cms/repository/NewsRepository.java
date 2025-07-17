package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.unap.oti.cms.model.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
    // Find published news ordered by creation date
    List<News> findByPublishedTrueOrderByCreatedAtDesc();
    
    // Find news by category and published status
    List<News> findByCategoryAndPublishedTrue(String category);
    
    // Find news by title containing text (case insensitive)
    List<News> findByTitleContainingIgnoreCase(String title);
    
    // Count published news
    long countByPublishedTrue();
    
    // Find news by category
    List<News> findByCategoryOrderByCreatedAtDesc(String category);
    
    // Find recent news (published)
    @Query("SELECT n FROM News n WHERE n.published = true ORDER BY n.createdAt DESC LIMIT 10")
    List<News> findRecentPublishedNews();
    
    // Find featured news
    @Query("SELECT n FROM News n WHERE n.published = true AND n.views > 100 ORDER BY n.views DESC")
    List<News> findFeaturedNews();
}