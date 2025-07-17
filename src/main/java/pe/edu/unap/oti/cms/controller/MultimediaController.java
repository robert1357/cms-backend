package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Multimedia;
import pe.edu.unap.oti.cms.service.MultimediaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/multimedia")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class MultimediaController {
    
    @Autowired
    private MultimediaService multimediaService;
    
    @GetMapping
    public ResponseEntity<List<Multimedia>> getAllMultimedia() {
        try {
            List<Multimedia> multimedia = multimediaService.findAll();
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Multimedia> getMultimediaById(@PathVariable Long id) {
        try {
            Optional<Multimedia> multimedia = multimediaService.findById(id);
            return multimedia.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Multimedia>> getActiveMultimedia() {
        try {
            List<Multimedia> multimedia = multimediaService.findActiveMultimedia();
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/type/{fileType}")
    public ResponseEntity<List<Multimedia>> getMultimediaByFileType(@PathVariable String fileType) {
        try {
            List<Multimedia> multimedia = multimediaService.findMultimediaByFileType(fileType);
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Multimedia>> getMultimediaByCategory(@PathVariable String category) {
        try {
            List<Multimedia> multimedia = multimediaService.findMultimediaByCategory(category);
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<Multimedia>> getFeaturedMultimedia() {
        try {
            List<Multimedia> multimedia = multimediaService.findFeaturedMultimedia();
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/gallery")
    public ResponseEntity<List<Multimedia>> getGalleryItems() {
        try {
            List<Multimedia> multimedia = multimediaService.findGalleryItems();
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/documents")
    public ResponseEntity<List<Multimedia>> getDocuments() {
        try {
            List<Multimedia> multimedia = multimediaService.findDocuments();
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Multimedia>> searchMultimedia(@RequestParam String title) {
        try {
            List<Multimedia> multimedia = multimediaService.searchByTitle(title);
            return ResponseEntity.ok(multimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Multimedia> createMultimedia(@RequestBody Multimedia multimedia) {
        try {
            Multimedia savedMultimedia = multimediaService.save(multimedia);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMultimedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Multimedia> updateMultimedia(@PathVariable Long id, @RequestBody Multimedia multimedia) {
        try {
            Multimedia updatedMultimedia = multimediaService.update(id, multimedia);
            return ResponseEntity.ok(updatedMultimedia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMultimedia(@PathVariable Long id) {
        try {
            multimediaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/{id}/download")
    public ResponseEntity<Void> incrementDownloadCount(@PathVariable Long id) {
        try {
            multimediaService.incrementDownloadCount(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}