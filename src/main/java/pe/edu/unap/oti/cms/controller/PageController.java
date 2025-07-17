package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.dto.PageDTO;
import pe.edu.unap.oti.cms.model.Page;
import pe.edu.unap.oti.cms.model.Category;
import pe.edu.unap.oti.cms.repository.PageRepository;
import pe.edu.unap.oti.cms.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Page> getAll() {
        return pageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page> getById(@PathVariable Long id) {
        return pageRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Page create(@RequestBody PageDTO dto) {
        Page page = new Page();
        page.setTitle(dto.getTitle());
        page.setContent(dto.getContent());
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            page.setCategory(cat);
        }
        // Generar slug simple
        String slug = dto.getTitle().toLowerCase().replaceAll("[^a-z0-9]+", "-");
        page.setSlug(slug);

        return pageRepository.save(page);
    }

    @PutMapping("/{id}")
    public Page update(@PathVariable Long id, @RequestBody PageDTO dto) {
        Page page = pageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Página no encontrada"));
        page.setTitle(dto.getTitle());
        page.setContent(dto.getContent());
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            page.setCategory(cat);
        }
        return pageRepository.save(page);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pageRepository.deleteById(id);
    }
}