package pe.edu.unap.oti.cms.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Category;
import pe.edu.unap.oti.cms.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        // Generar el slug si no viene del frontend
        if (category.getSlug() == null || category.getSlug().isEmpty()) {
            String baseSlug = slugify(category.getName());
            String uniqueSlug = baseSlug;
            int counter = 1;
            // Verificar que el slug sea único
            while (categoryRepository.existsBySlug(uniqueSlug)) {
                uniqueSlug = baseSlug + "-" + counter;
                counter++;
            }
            category.setSlug(uniqueSlug);
        }
        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(saved);
    }

    private String slugify(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryRepository.findById(id)
            .map(category -> {
                category.setName(updatedCategory.getName());
                category.setDescription(updatedCategory.getDescription());
                // Actualiza el slug si lo deseas, o mantenlo igual
                return ResponseEntity.ok(categoryRepository.save(category));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) return ResponseEntity.notFound().build();
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}