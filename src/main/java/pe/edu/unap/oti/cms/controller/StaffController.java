package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Staff;
import pe.edu.unap.oti.cms.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {
    
    private static final Logger log = LoggerFactory.getLogger(StaffController.class);
    
    @Autowired
    private StaffService staffService;
    
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        try {
            List<Staff> staff = staffService.findAll();
            log.info("Successfully retrieved {} staff members", staff.size());
            return ResponseEntity.ok(staff);
        } catch (Exception e) {
            log.error("Error retrieving all staff", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        try {
            Optional<Staff> staff = staffService.findById(id);
            return staff.map(s -> {
                log.info("Successfully retrieved staff member with ID: {}", id);
                return ResponseEntity.ok(s);
            }).orElseGet(() -> {
                log.warn("Staff member not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
        } catch (Exception e) {
            log.error("Error retrieving staff member with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Staff>> getActiveStaff() {
        try {
            List<Staff> activeStaff = staffService.findActiveStaff();
            log.info("Successfully retrieved {} active staff members", activeStaff.size());
            return ResponseEntity.ok(activeStaff);
        } catch (Exception e) {
            log.error("Error retrieving active staff", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Staff>> getStaffByDepartment(@PathVariable String department) {
        try {
            List<Staff> staff = staffService.findStaffByDepartment(department);
            return ResponseEntity.ok(staff);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Staff>> searchStaff(@RequestParam String name) {
        try {
            List<Staff> staff = staffService.searchByName(name);
            return ResponseEntity.ok(staff);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        try {
            Staff savedStaff = staffService.save(staff);
            log.info("Successfully created staff member: {}", savedStaff.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStaff);
        } catch (Exception e) {
            log.error("Error creating staff member", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        try {
            Staff updatedStaff = staffService.update(id, staff);
            log.info("Successfully updated staff member: {}", updatedStaff.getName());
            return ResponseEntity.ok(updatedStaff);
        } catch (RuntimeException e) {
            log.warn("Staff member not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating staff member with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        try {
            staffService.delete(id);
            log.info("Successfully deleted staff member with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Staff member not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting staff member with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}