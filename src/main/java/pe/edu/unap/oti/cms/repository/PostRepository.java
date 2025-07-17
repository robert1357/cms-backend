package pe.edu.unap.oti.cms.repository;

import pe.edu.unap.oti.cms.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    // Encontrar posts publicados ordenados por fecha de creación
    List<Post> findByPublishedTrueOrderByCreatedAtDesc();
    
    // Encontrar posts destacados y publicados
    List<Post> findByPublishedTrueAndFeaturedTrueOrderByCreatedAtDesc();
    
    // Encontrar post por ID solo si está publicado
    Optional<Post> findByIdAndPublishedTrue(Long id);
    
    // Contar posts publicados
    long countByPublishedTrue();
    
    // Buscar posts por título (para búsqueda)
    @Query("SELECT p FROM Post p WHERE p.published = true AND LOWER(p.title) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Post> findByTitleContainingIgnoreCaseAndPublishedTrue(String title);
    
    // Encontrar posts hero (para video intro)
    List<Post> findByHeroPostTrueAndPublishedTrue();
    
    // Encontrar top 6 posts recientes
    @Query("SELECT p FROM Post p WHERE p.published = true ORDER BY p.createdAt DESC LIMIT 6")
    List<Post> findTop6ByPublishedTrueOrderByCreatedAtDesc();
}