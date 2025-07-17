package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.unap.oti.cms.model.Page;
import java.util.List;
import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findBySlug(String slug);
    Optional<Page> findBySlugAndPublishedTrue(String slug);
    List<Page> findByCategoryId(Long categoryId);
    List<Page> findByPublishedTrue();
    List<Page> findByPublishedTrueOrderByCreatedAtDesc();
    boolean existsBySlug(String slug);
    long countByPublishedTrue();
    
    // Buscar páginas por título (para búsqueda)
    @Query("SELECT p FROM Page p WHERE p.published = true AND LOWER(p.title) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Page> findByTitleContainingIgnoreCaseAndPublishedTrue(String title);
}