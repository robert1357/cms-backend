package pe.edu.unap.oti.cms.repository;

import pe.edu.unap.oti.cms.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    
    // Find active services
    List<Service> findByActiveTrue();
    
    // Count active services
    long countByActiveTrue();
    
    // Find services by target audience
    List<Service> findByTargetAudienceAndActiveTrue(String targetAudience);
    
    // Find services by category
    List<Service> findByCategoryAndActiveTrue(String category);
    
    // Find services ordered by priority
    List<Service> findByActiveTrueOrderByPriorityAsc();
    
    // Find active services ordered by sort order
    List<Service> findByActiveTrueOrderBySortOrderAsc();
    
    // Find services by title (case insensitive)
    List<Service> findByTitleContainingIgnoreCaseAndActiveTrue(String title);
    
    // Find top services
    @Query("SELECT s FROM Service s WHERE s.active = true ORDER BY s.priority ASC LIMIT 6")
    List<Service> findTopActiveServices();
    
    // Find services by audience including both
    @Query("SELECT s FROM Service s WHERE s.active = true AND (s.targetAudience = 'both' OR s.targetAudience = :audience) ORDER BY s.priority ASC")
    List<Service> findServicesByAudienceIncludingBoth(@Param("audience") String audience);
    
    // Find recent services (active)
    @Query("SELECT s FROM Service s WHERE s.active = true ORDER BY s.createdAt DESC LIMIT 10")
    List<Service> findRecentActiveServices();
    
    // Find popular services
    @Query("SELECT s FROM Service s WHERE s.active = true AND s.views > 50 ORDER BY s.views DESC")
    List<Service> findPopularServices();
}