package pe.edu.unap.oti.cms.dto;

public class DashboardStats {
    private Long totalNews;
    private Long publishedNews;
    private Long draftNews;
    private Long totalServices;
    private Long activeServices;
    private Long totalStaff;
    private Long totalActivities;
    private Long upcomingActivities;
    private Long totalMultimedia;
    private Long recentViews;
    private Long monthlyGrowth;

    // Default constructor
    public DashboardStats() {}

    // Constructor with all fields
    public DashboardStats(Long totalNews, Long publishedNews, Long draftNews, 
                         Long totalServices, Long activeServices, Long totalStaff,
                         Long totalActivities, Long upcomingActivities, Long totalMultimedia,
                         Long recentViews, Long monthlyGrowth) {
        this.totalNews = totalNews;
        this.publishedNews = publishedNews;
        this.draftNews = draftNews;
        this.totalServices = totalServices;
        this.activeServices = activeServices;
        this.totalStaff = totalStaff;
        this.totalActivities = totalActivities;
        this.upcomingActivities = upcomingActivities;
        this.totalMultimedia = totalMultimedia;
        this.recentViews = recentViews;
        this.monthlyGrowth = monthlyGrowth;
    }

    // Getters and Setters
    public Long getTotalNews() {
        return totalNews;
    }

    public void setTotalNews(Long totalNews) {
        this.totalNews = totalNews;
    }

    public Long getPublishedNews() {
        return publishedNews;
    }

    public void setPublishedNews(Long publishedNews) {
        this.publishedNews = publishedNews;
    }

    public Long getDraftNews() {
        return draftNews;
    }

    public void setDraftNews(Long draftNews) {
        this.draftNews = draftNews;
    }

    public Long getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(Long totalServices) {
        this.totalServices = totalServices;
    }

    public Long getActiveServices() {
        return activeServices;
    }

    public void setActiveServices(Long activeServices) {
        this.activeServices = activeServices;
    }

    public Long getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(Long totalStaff) {
        this.totalStaff = totalStaff;
    }

    public Long getTotalActivities() {
        return totalActivities;
    }

    public void setTotalActivities(Long totalActivities) {
        this.totalActivities = totalActivities;
    }

    public Long getUpcomingActivities() {
        return upcomingActivities;
    }

    public void setUpcomingActivities(Long upcomingActivities) {
        this.upcomingActivities = upcomingActivities;
    }

    public Long getTotalMultimedia() {
        return totalMultimedia;
    }

    public void setTotalMultimedia(Long totalMultimedia) {
        this.totalMultimedia = totalMultimedia;
    }

    public Long getRecentViews() {
        return recentViews;
    }

    public void setRecentViews(Long recentViews) {
        this.recentViews = recentViews;
    }

    public Long getMonthlyGrowth() {
        return monthlyGrowth;
    }

    public void setMonthlyGrowth(Long monthlyGrowth) {
        this.monthlyGrowth = monthlyGrowth;
    }
}