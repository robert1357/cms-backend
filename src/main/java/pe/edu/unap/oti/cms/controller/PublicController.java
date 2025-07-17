package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Post;
import pe.edu.unap.oti.cms.model.Page;
import pe.edu.unap.oti.cms.model.Event;
import pe.edu.unap.oti.cms.repository.PostRepository;
import pe.edu.unap.oti.cms.repository.PageRepository;
import pe.edu.unap.oti.cms.repository.EventRepository;
import pe.edu.unap.oti.cms.repository.ServiceRepository;
import pe.edu.unap.oti.cms.model.Service;
import pe.edu.unap.oti.cms.repository.StaffRepository;
import pe.edu.unap.oti.cms.model.Staff;
import pe.edu.unap.oti.cms.repository.ActivityRepository;
import pe.edu.unap.oti.cms.model.Activity;
import pe.edu.unap.oti.cms.repository.MultimediaRepository;
import pe.edu.unap.oti.cms.model.Multimedia;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class PublicController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private MultimediaRepository multimediaRepository;

    // Obtener posts publicados y destacados para la página principal
    @GetMapping("/featured-posts")
    public List<Post> getFeaturedPosts() {
        return postRepository.findByPublishedTrueAndFeaturedTrueOrderByCreatedAtDesc();
    }

    // Obtener posts publicados recientes
    @GetMapping("/recent-posts")
    public List<Post> getRecentPosts(@RequestParam(defaultValue = "6") int limit) {
        return postRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .toList();
    }

    // Obtener un post específico por ID
    @GetMapping("/posts/{id}")
    public Optional<Post> getPost(@PathVariable Long id) {
        return postRepository.findByIdAndPublishedTrue(id);
    }

    // Obtener páginas publicadas
    @GetMapping("/pages")
    public List<Page> getPublishedPages() {
        return pageRepository.findByPublishedTrueOrderByCreatedAtDesc();
    }

    // Obtener página por slug
    @GetMapping("/pages/{slug}")
    public Optional<Page> getPageBySlug(@PathVariable String slug) {
        return pageRepository.findBySlugAndPublishedTrue(slug);
    }

    // Obtener eventos próximos
    @GetMapping("/upcoming-events")
    public List<Event> getUpcomingEvents(@RequestParam(defaultValue = "5") int limit) {
        return eventRepository.findByPublishedTrueOrderByStartDateDesc()
                .stream()
                .limit(limit)
                .toList();
    }

    // Obtener servicios activos
    @GetMapping("/services")
    public List<Service> getActiveServices() {
        return serviceRepository.findByActiveTrueOrderBySortOrderAsc();
    }

    // Obtener servicios para estudiantes
    @GetMapping("/services/students")
    public List<Service> getServicesForStudents() {
        return serviceRepository.findServicesByAudienceIncludingBoth("students");
    }

    // Obtener servicios para docentes
    @GetMapping("/services/teachers")
    public List<Service> getServicesForTeachers() {
        return serviceRepository.findServicesByAudienceIncludingBoth("teachers");
    }

    // Buscar contenido
    @GetMapping("/search")
    public SearchResponse searchContent(@RequestParam String query) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCaseAndPublishedTrue(query);
        List<Page> pages = pageRepository.findByTitleContainingIgnoreCaseAndPublishedTrue(query);
        List<Service> services = serviceRepository.findByTitleContainingIgnoreCaseAndActiveTrue(query);
        
        return new SearchResponse(posts, pages, services);
    }

    // Obtener post principal (hero post)
    @GetMapping("/hero-post")
    public Optional<Post> getHeroPost() {
        List<Post> heroPosts = postRepository.findByHeroPostTrueAndPublishedTrue();
        return heroPosts.isEmpty() ? Optional.empty() : Optional.of(heroPosts.get(0));
    }

    // Estadísticas generales para la página principal
    @GetMapping("/stats")
    public PublicStatsResponse getPublicStats() {
        long totalPosts = postRepository.countByPublishedTrue();
        long totalPages = pageRepository.countByPublishedTrue();
        long totalEvents = eventRepository.count();
        long totalServices = serviceRepository.count();
        
        return new PublicStatsResponse(totalPosts, totalPages, totalEvents, totalServices);
    }

    // Clase interna para respuesta de estadísticas
    public static class PublicStatsResponse {
        private long totalPosts;
        private long totalPages;
        private long totalEvents;
        private long totalServices;
        private int yearsOfService = 24; // Como en la imagen de referencia

        public PublicStatsResponse(long totalPosts, long totalPages, long totalEvents, long totalServices) {
            this.totalPosts = totalPosts;
            this.totalPages = totalPages;
            this.totalEvents = totalEvents;
            this.totalServices = totalServices;
        }

        // Getters
        public long getTotalPosts() { return totalPosts; }
        public long getTotalPages() { return totalPages; }
        public long getTotalEvents() { return totalEvents; }
        public long getTotalServices() { return totalServices; }
        public int getYearsOfService() { return yearsOfService; }
    }

    // Clase interna para respuesta de búsqueda
    public static class SearchResponse {
        private List<Post> posts;
        private List<Page> pages;
        private List<Service> services;

        public SearchResponse(List<Post> posts, List<Page> pages, List<Service> services) {
            this.posts = posts;
            this.pages = pages;
            this.services = services;
        }

        // Getters
        public List<Post> getPosts() { return posts; }
        public List<Page> getPages() { return pages; }
        public List<Service> getServices() { return services; }
    }
    
    // Nuevos endpoints para Staff, Activities y Multimedia
    
    // Obtener personal/staff activo
    @GetMapping("/staff")
    public List<Staff> getStaff() {
        return staffRepository.findByActiveTrueOrderBySortOrderAsc();
    }
    
    // Obtener staff por departamento
    @GetMapping("/staff/department/{department}")
    public List<Staff> getStaffByDepartment(@PathVariable String department) {
        return staffRepository.findByDepartmentAndActiveTrueOrderBySortOrderAsc(department);
    }
    
    // Obtener actividades publicadas
    @GetMapping("/activities")
    public List<Activity> getActivities() {
        return activityRepository.findByPublishedTrueOrderByStartDateDesc();
    }
    
    // Obtener actividades destacadas
    @GetMapping("/activities/featured")
    public List<Activity> getFeaturedActivities() {
        return activityRepository.findByPublishedTrueAndFeaturedTrueOrderByStartDateDesc();
    }
    
    // Obtener próximas actividades
    @GetMapping("/activities/upcoming")
    public List<Activity> getUpcomingActivities() {
        return activityRepository.findUpcomingActivities(java.time.LocalDateTime.now());
    }
    
    // Obtener actividades por audiencia objetivo
    @GetMapping("/activities/audience/{audience}")
    public List<Activity> getActivitiesByAudience(@PathVariable String audience) {
        return activityRepository.findByPublishedTrueAndTargetAudienceOrderByStartDateDesc(audience);
    }
    
    // Obtener actividad por slug
    @GetMapping("/activities/{slug}")
    public Activity getActivityBySlug(@PathVariable String slug) {
        return activityRepository.findBySlugAndPublishedTrue(slug)
            .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
    }
    
    // Obtener multimedia pública
    @GetMapping("/multimedia")
    public List<Multimedia> getMultimedia() {
        return multimediaRepository.findByIsPublicTrueOrderByCreatedAtDesc();
    }
    
    // Obtener multimedia por tipo
    @GetMapping("/multimedia/type/{type}")
    public List<Multimedia> getMultimediaByType(@PathVariable String type) {
        return multimediaRepository.findByFileTypeAndIsPublicTrueOrderByCreatedAtDesc(type);
    }
    
    // Obtener galería (imágenes y videos)
    @GetMapping("/multimedia/gallery")
    public List<Multimedia> getGallery() {
        return multimediaRepository.findGalleryItems();
    }
    
    // Obtener documentos
    @GetMapping("/multimedia/documents")
    public List<Multimedia> getDocuments() {
        return multimediaRepository.findDocuments();
    }
    
    // Obtener multimedia por categoría
    @GetMapping("/multimedia/category/{category}")
    public List<Multimedia> getMultimediaByCategory(@PathVariable String category) {
        return multimediaRepository.findByCategoryAndIsPublicTrueOrderByCreatedAtDesc(category);
    }
    
    // Nuevos endpoints para funcionalidades avanzadas OTI
    
    // Obtener resumen completo para dashboard público
    @GetMapping("/dashboard-summary")
    public DashboardSummaryResponse getDashboardSummary() {
        DashboardSummaryResponse summary = new DashboardSummaryResponse();
        
        // Conteos básicos
        summary.totalPosts = postRepository.countByPublishedTrue();
        summary.totalPages = pageRepository.countByPublishedTrue();
        summary.totalEvents = eventRepository.count();
        summary.totalServices = serviceRepository.count();
        summary.totalStaff = staffRepository.findByActiveTrueOrderBySortOrderAsc().size();
        summary.totalActivities = activityRepository.count();
        summary.totalMultimedia = multimediaRepository.count();
        
        // Posts recientes
        summary.recentPosts = postRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream().limit(3).toList();
        
        // Próximas actividades
        summary.upcomingActivities = activityRepository.findUpcomingActivities(java.time.LocalDateTime.now())
                .stream().limit(3).toList();
                
        // Staff destacado (primeros 4)
        summary.featuredStaff = staffRepository.findByActiveTrueOrderBySortOrderAsc()
                .stream().limit(4).toList();
        
        return summary;
    }
    
    // Búsqueda avanzada con filtros
    @GetMapping("/advanced-search")
    public AdvancedSearchResponse advancedSearch(
            @RequestParam String query,
            @RequestParam(required = false) String contentType,
            @RequestParam(required = false) String department) {
        
        AdvancedSearchResponse result = new AdvancedSearchResponse();
        
        if (contentType == null || contentType.equals("posts")) {
            result.posts = postRepository.findByTitleContainingIgnoreCaseAndPublishedTrue(query);
        }
        
        if (contentType == null || contentType.equals("activities")) {
            result.activities = activityRepository.findByTitleContainingIgnoreCaseAndPublishedTrue(query);
        }
        
        if (contentType == null || contentType.equals("staff")) {
            if (department != null) {
                result.staff = staffRepository.findByDepartmentAndActiveTrueOrderBySortOrderAsc(department)
                        .stream()
                        .filter(s -> s.getFullName().toLowerCase().contains(query.toLowerCase()))
                        .toList();
            } else {
                result.staff = staffRepository.findByActiveTrueOrderBySortOrderAsc()
                        .stream()
                        .filter(s -> s.getFullName().toLowerCase().contains(query.toLowerCase()))
                        .toList();
            }
        }
        
        if (contentType == null || contentType.equals("services")) {
            result.services = serviceRepository.findByTitleContainingIgnoreCaseAndActiveTrue(query);
        }
        
        return result;
    }
    
    // Obtener todas las categorías disponibles
    @GetMapping("/categories-summary")
    public CategoriesSummaryResponse getCategoriesSummary() {
        CategoriesSummaryResponse summary = new CategoriesSummaryResponse();
        
        // Departamentos únicos del staff
        summary.departments = staffRepository.findByActiveTrueOrderBySortOrderAsc()
                .stream()
                .map(Staff::getDepartment)
                .distinct()
                .sorted()
                .toList();
                
        // Tipos de audiencia de servicios
        summary.serviceAudiences = serviceRepository.findByActiveTrueOrderBySortOrderAsc()
                .stream()
                .map(Service::getTargetAudience)
                .distinct()
                .sorted()
                .toList();
                
        // Tipos de actividades por audiencia
        summary.activityAudiences = activityRepository.findByPublishedTrueOrderByStartDateDesc()
                .stream()
                .map(Activity::getTargetAudience)
                .distinct()
                .sorted()
                .toList();
                
        // Categorías de multimedia
        summary.multimediaCategories = multimediaRepository.findByIsPublicTrueOrderByCreatedAtDesc()
                .stream()
                .map(Multimedia::getCategory)
                .distinct()
                .sorted()
                .toList();
        
        return summary;
    }
    
    // Obtener calendario de actividades por mes
    @GetMapping("/activities-calendar")
    public List<ActivityCalendarEntry> getActivitiesCalendar(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String year) {
        
        List<Activity> activities = activityRepository.findByPublishedTrueOrderByStartDateDesc();
        
        return activities.stream()
                .map(activity -> {
                    ActivityCalendarEntry entry = new ActivityCalendarEntry();
                    entry.id = activity.getId();
                    entry.title = activity.getTitle();
                    entry.startDate = activity.getStartDate();
                    entry.endDate = activity.getEndDate();
                    entry.location = activity.getLocation();
                    entry.targetAudience = activity.getTargetAudience();
                    entry.status = activity.getStatus().toString();
                    return entry;
                })
                .toList();
    }
    
    // Clases internas para respuestas de endpoints avanzados
    public static class DashboardSummaryResponse {
        public long totalPosts;
        public long totalPages;
        public long totalEvents;
        public long totalServices;
        public long totalStaff;
        public long totalActivities;
        public long totalMultimedia;
        public List<Post> recentPosts;
        public List<Activity> upcomingActivities;
        public List<Staff> featuredStaff;
        
        // Getters para serialización JSON
        public long getTotalPosts() { return totalPosts; }
        public long getTotalPages() { return totalPages; }
        public long getTotalEvents() { return totalEvents; }
        public long getTotalServices() { return totalServices; }
        public long getTotalStaff() { return totalStaff; }
        public long getTotalActivities() { return totalActivities; }
        public long getTotalMultimedia() { return totalMultimedia; }
        public List<Post> getRecentPosts() { return recentPosts; }
        public List<Activity> getUpcomingActivities() { return upcomingActivities; }
        public List<Staff> getFeaturedStaff() { return featuredStaff; }
    }
    
    public static class AdvancedSearchResponse {
        public List<Post> posts = new ArrayList<>();
        public List<Activity> activities = new ArrayList<>();
        public List<Staff> staff = new ArrayList<>();
        public List<Service> services = new ArrayList<>();
        
        public List<Post> getPosts() { return posts; }
        public List<Activity> getActivities() { return activities; }
        public List<Staff> getStaff() { return staff; }
        public List<Service> getServices() { return services; }
    }
    
    public static class CategoriesSummaryResponse {
        public List<String> departments = new ArrayList<>();
        public List<String> serviceAudiences = new ArrayList<>();
        public List<String> activityAudiences = new ArrayList<>();
        public List<String> multimediaCategories = new ArrayList<>();
        
        public List<String> getDepartments() { return departments; }
        public List<String> getServiceAudiences() { return serviceAudiences; }
        public List<String> getActivityAudiences() { return activityAudiences; }
        public List<String> getMultimediaCategories() { return multimediaCategories; }
    }
    
    public static class ActivityCalendarEntry {
        public Long id;
        public String title;
        public java.time.LocalDateTime startDate;
        public java.time.LocalDateTime endDate;
        public String location;
        public String targetAudience;
        public String status;
        
        public Long getId() { return id; }
        public String getTitle() { return title; }
        public java.time.LocalDateTime getStartDate() { return startDate; }
        public java.time.LocalDateTime getEndDate() { return endDate; }
        public String getLocation() { return location; }
        public String getTargetAudience() { return targetAudience; }
        public String getStatus() { return status; }
    }
}