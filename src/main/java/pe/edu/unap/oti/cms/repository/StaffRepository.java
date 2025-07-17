package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.unap.oti.cms.model.Staff;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    
    // Find active staff ordered by priority
    List<Staff> findByActiveTrueOrderByPriorityAsc();
    
    // Find active staff ordered by sort order
    List<Staff> findByActiveTrueOrderBySortOrderAsc();
    
    // Find staff by department
    List<Staff> findByDepartmentAndActiveTrueOrderByPriorityAsc(String department);
    
    // Find staff by department ordered by sort order
    List<Staff> findByDepartmentAndActiveTrueOrderBySortOrderAsc(String department);
    
    // Find staff by name (case insensitive)
    List<Staff> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    
    // Find staff by position (case insensitive)
    List<Staff> findByPositionContainingIgnoreCaseAndActiveTrue(String position);
    
    // Find staff by email
    Staff findByEmailAndActiveTrue(String email);
    
    // Find staff by active status
    List<Staff> findByActiveOrderByPriorityAsc(boolean active);
    
    // Find top staff members
    @Query("SELECT s FROM Staff s WHERE s.active = true ORDER BY s.priority ASC LIMIT 6")
    List<Staff> findTopActiveStaff();
}