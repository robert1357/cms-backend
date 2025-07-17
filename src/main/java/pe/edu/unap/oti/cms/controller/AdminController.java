package pe.edu.unap.oti.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String dashboard(Model model) {
        // Aquí irá la lógica del dashboard
        return "admin/dashboard";
    }
    
    @GetMapping("/categories")
    public String categories(Model model) {
        // Aquí irá la lógica de categorías
        return "admin/categories";
    }
    
    @GetMapping("/pages")
    public String pages(Model model) {
        // Aquí irá la lógica de páginas
        return "admin/pages";
    }
}