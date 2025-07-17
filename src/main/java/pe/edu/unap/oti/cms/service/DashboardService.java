package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unap.oti.cms.dto.DashboardStats;
import pe.edu.unap.oti.cms.repository.*;

import java.time.LocalDateTime;

@Service
public class DashboardService {
    
    @Autowired
    private NewsRepository newsRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private MultimediaRepository multimediaRepository;
    
    public DashboardStats getStats() {
        long totalNews = newsRepository.count();
        long publishedNews = newsRepository.countByPublishedTrue();
        long draftNews = totalNews - publishedNews;
        
        long totalServices = serviceRepository.count();
        long activeServices = serviceRepository.countByActiveTrue();
        
        long totalStaff = staffRepository.count();
        
        long totalActivities = activityRepository.count();
        long upcomingActivities = activityRepository.countByStartDateAfter(LocalDateTime.now());
        
        long totalMultimedia = multimediaRepository.count();
        
        return new DashboardStats(
            totalNews,
            publishedNews,
            draftNews,
            totalServices,
            activeServices,
            totalStaff,
            totalActivities,
            upcomingActivities,
            totalMultimedia,
            0L, // recentViews - can be implemented later
            0L  // monthlyGrowth - can be implemented later
        );
    }
}