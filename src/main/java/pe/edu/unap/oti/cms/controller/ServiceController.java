package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Service;
import pe.edu.unap.oti.cms.service.ServiceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        try {
            List<Service> services = serviceService.findAll();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        try {
            Optional<Service> service = serviceService.findById(id);
            if (service.isPresent()) {
                // Increment view count
                serviceService.incrementViews(id);
                return ResponseEntity.ok(service.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Service>> getActiveServices() {
        try {
            List<Service> services = serviceService.findActiveServices();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/audience/{targetAudience}")
    public ResponseEntity<List<Service>> getServicesByTargetAudience(@PathVariable String targetAudience) {
        try {
            List<Service> services = serviceService.findServicesByTargetAudience(targetAudience);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Service>> getServicesByCategory(@PathVariable String category) {
        try {
            List<Service> services = serviceService.findServicesByCategory(category);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Service>> searchServices(@RequestParam String title) {
        try {
            List<Service> services = serviceService.searchByTitle(title);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        try {
            Service savedService = serviceService.save(service);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service service) {
        try {
            Service updatedService = serviceService.update(id, service);
            return ResponseEntity.ok(updatedService);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            serviceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}