package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unap.oti.cms.model.Activity;
import pe.edu.unap.oti.cms.repository.ActivityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityService {
    
    @Autowired
    private ActivityRepository activityRepository;
    
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }
    
    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }
    
    public List<Activity> findPublishedActivities() {
        return activityRepository.findByPublishedTrueOrderByStartDateDesc();
    }
    
    public List<Activity> findFeaturedActivities() {
        return activityRepository.findByPublishedTrueAndFeaturedTrueOrderByStartDateDesc();
    }
    
    public List<Activity> findActivitiesByStatus(Activity.ActivityStatus status) {
        return activityRepository.findByPublishedTrueAndStatusOrderByStartDateDesc(status);
    }
    
    public List<Activity> findActivitiesByTargetAudience(String targetAudience) {
        return activityRepository.findByPublishedTrueAndTargetAudienceOrderByStartDateDesc(targetAudience);
    }
    
    public List<Activity> findActivitiesByCategory(String category) {
        return activityRepository.findByPublishedTrueAndCategoryOrderByStartDateDesc(category);
    }
    
    public List<Activity> searchByTitle(String title) {
        return activityRepository.findByTitleContainingIgnoreCaseAndPublishedTrue(title);
    }
    
    public List<Activity> searchByLocation(String location) {
        return activityRepository.findByLocationContainingIgnoreCaseAndPublishedTrue(location);
    }
    
    public List<Activity> findUpcomingActivities() {
        return activityRepository.findUpcomingActivities(LocalDateTime.now());
    }
    
    public List<Activity> findActivitiesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return activityRepository.findActivitiesBetweenDates(startDate, endDate);
    }
    
    public Activity save(Activity activity) {
        // Set default status if not provided
        if (activity.getStatus() == null) {
            activity.setStatus(Activity.ActivityStatus.SCHEDULED);
        }
        
        // Validate dates
        if (activity.getStartDate() != null && activity.getEndDate() != null) {
            if (activity.getStartDate().isAfter(activity.getEndDate())) {
                throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de fin");
            }
        }
        
        // Validate registration deadline
        if (activity.getRegistrationRequired() && activity.getRegistrationDeadline() != null) {
            if (activity.getRegistrationDeadline().isAfter(activity.getStartDate())) {
                throw new RuntimeException("La fecha límite de inscripción no puede ser posterior a la fecha de inicio");
            }
        }
        
        return activityRepository.save(activity);
    }
    
    public Activity update(Long id, Activity activityData) {
        Activity existingActivity = activityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Actividad no encontrada con ID: " + id));
        
        // Validate dates
        if (activityData.getStartDate() != null && activityData.getEndDate() != null) {
            if (activityData.getStartDate().isAfter(activityData.getEndDate())) {
                throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de fin");
            }
        }
        
        // Validate registration deadline
        if (activityData.getRegistrationRequired() && activityData.getRegistrationDeadline() != null) {
            if (activityData.getRegistrationDeadline().isAfter(activityData.getStartDate())) {
                throw new RuntimeException("La fecha límite de inscripción no puede ser posterior a la fecha de inicio");
            }
        }
        
        // Update basic fields
        existingActivity.setTitle(activityData.getTitle());
        existingActivity.setDescription(activityData.getDescription());
        existingActivity.setFullDescription(activityData.getFullDescription());
        existingActivity.setLocation(activityData.getLocation());
        existingActivity.setStartDate(activityData.getStartDate());
        existingActivity.setEndDate(activityData.getEndDate());
        existingActivity.setCategory(activityData.getCategory());
        existingActivity.setTargetAudience(activityData.getTargetAudience());
        existingActivity.setCapacity(activityData.getCapacity());
        existingActivity.setRegistrationRequired(activityData.getRegistrationRequired());
        existingActivity.setRegistrationDeadline(activityData.getRegistrationDeadline());
        existingActivity.setContactEmail(activityData.getContactEmail());
        existingActivity.setContactPhone(activityData.getContactPhone());
        existingActivity.setImageUrl(activityData.getImageUrl());
        existingActivity.setTags(activityData.getTags());
        existingActivity.setPublished(activityData.getPublished());
        existingActivity.setFeatured(activityData.getFeatured());
        existingActivity.setStatus(activityData.getStatus());
        
        return activityRepository.save(existingActivity);
    }
    
    public void delete(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new RuntimeException("Actividad no encontrada con ID: " + id);
        }
        activityRepository.deleteById(id);
    }
    
    public long getTotalActivitiesCount() {
        return activityRepository.count();
    }
    
    public long getPublishedActivitiesCount() {
        return activityRepository.countPublishedActivities();
    }
    
    public long getUpcomingActivitiesCount() {
        return activityRepository.countByStartDateAfter(LocalDateTime.now());
    }
    
    public void incrementViews(Long id) {
        Optional<Activity> activityOptional = activityRepository.findById(id);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            activity.setViews(activity.getViews() + 1);
            activityRepository.save(activity);
        }
    }
}