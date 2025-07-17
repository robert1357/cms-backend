package pe.edu.unap.oti.cms.repository;

import pe.edu.unap.oti.cms.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    
    // Encontrar eventos próximos (después de la fecha actual) ordenados por fecha
    @Query("SELECT e FROM Event e WHERE e.published = true AND e.startDate >= :currentDate ORDER BY e.startDate ASC")
    List<Event> findUpcomingEventsOrderByEventDate();
    
    // Encontrar eventos publicados ordenados por fecha
    List<Event> findByPublishedTrueOrderByStartDateDesc();
    
    // Eventos entre fechas específicas
    @Query("SELECT e FROM Event e WHERE e.published = true AND e.startDate >= :startDate AND e.startDate <= :endDate ORDER BY e.startDate ASC")
    List<Event> findEventsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}