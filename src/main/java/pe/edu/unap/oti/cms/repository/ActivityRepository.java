package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.unap.oti.cms.model.Activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
    // Find published activities ordered by start date
    List<Activity> findByPublishedTrueOrderByStartDateDesc();
    
    // Find featured activities
    List<Activity> findByPublishedTrueAndFeaturedTrueOrderByStartDateDesc();
    
    // Find activities by status
    List<Activity> findByPublishedTrueAndStatusOrderByStartDateDesc(Activity.ActivityStatus status);
    
    // Find activities by target audience
    List<Activity> findByPublishedTrueAndTargetAudienceOrderByStartDateDesc(String targetAudience);
    
    // Find activities by category
    List<Activity> findByPublishedTrueAndCategoryOrderByStartDateDesc(String category);
    
    // Find activities by title (case insensitive)
    List<Activity> findByTitleContainingIgnoreCaseAndPublishedTrue(String title);
    
    // Find activity by slug and published status
    Optional<Activity> findBySlugAndPublishedTrue(String slug);
    
    // Find activities by location (case insensitive)
    List<Activity> findByLocationContainingIgnoreCaseAndPublishedTrue(String location);
    
    // Find upcoming activities
    @Query("SELECT a FROM Activity a WHERE a.published = true AND a.startDate >= :now ORDER BY a.startDate ASC")
    List<Activity> findUpcomingActivities(LocalDateTime now);
    
    // Count upcoming activities
    @Query("SELECT COUNT(a) FROM Activity a WHERE a.startDate > :now")
    long countByStartDateAfter(LocalDateTime now);
    
    // Find activities between dates
    @Query("SELECT a FROM Activity a WHERE a.published = true AND a.startDate >= :startDate AND a.endDate <= :endDate ORDER BY a.startDate ASC")
    List<Activity> findActivitiesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Count published activities
    @Query("SELECT COUNT(a) FROM Activity a WHERE a.published = true")
    long countPublishedActivities();
    
    // Find recent activities (published)
    @Query("SELECT a FROM Activity a WHERE a.published = true ORDER BY a.createdAt DESC LIMIT 10")
    List<Activity> findRecentPublishedActivities();
    
    // Find featured activities
    @Query("SELECT a FROM Activity a WHERE a.published = true AND a.views > 100 ORDER BY a.views DESC")
    List<Activity> findPopularActivities();
}