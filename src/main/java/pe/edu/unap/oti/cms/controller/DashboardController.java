package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.dto.DashboardStats;
import pe.edu.unap.oti.cms.service.DashboardService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        try {
            DashboardStats stats = dashboardService.getStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}