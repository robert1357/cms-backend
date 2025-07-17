package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Activity;
import pe.edu.unap.oti.cms.service.ActivityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        try {
            List<Activity> activities = activityService.findAll();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        try {
            Optional<Activity> activity = activityService.findById(id);
            if (activity.isPresent()) {
                // Increment view count
                activityService.incrementViews(id);
                return ResponseEntity.ok(activity.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/published")
    public ResponseEntity<List<Activity>> getPublishedActivities() {
        try {
            List<Activity> activities = activityService.findPublishedActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Activity>> getFeaturedActivities() {
        try {
            List<Activity> activities = activityService.findFeaturedActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Activity>> getUpcomingActivities() {
        try {
            List<Activity> activities = activityService.findUpcomingActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Activity>> getActivitiesByStatus(@PathVariable String status) {
        try {
            Activity.ActivityStatus activityStatus = Activity.ActivityStatus.valueOf(status.toUpperCase());
            List<Activity> activities = activityService.findActivitiesByStatus(activityStatus);
            return ResponseEntity.ok(activities);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Activity>> getActivitiesByCategory(@PathVariable String category) {
        try {
            List<Activity> activities = activityService.findActivitiesByCategory(category);
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Activity>> searchActivities(@RequestParam String title) {
        try {
            List<Activity> activities = activityService.searchByTitle(title);
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        try {
            Activity savedActivity = activityService.save(activity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        try {
            Activity updatedActivity = activityService.update(id, activity);
            return ResponseEntity.ok(updatedActivity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        try {
            activityService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}