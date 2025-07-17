package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.unap.oti.cms.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);
}