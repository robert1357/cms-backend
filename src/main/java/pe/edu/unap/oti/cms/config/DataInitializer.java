package pe.edu.unap.oti.cms.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.unap.oti.cms.model.Role;
import pe.edu.unap.oti.cms.model.User;
import pe.edu.unap.oti.cms.repository.RoleRepository;
import pe.edu.unap.oti.cms.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository, 
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Iniciando creación de datos...");

            // Crear rol ADMIN si no existe
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    System.out.println("Creando rol ROLE_ADMIN...");
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

            System.out.println("Rol ADMIN ID: " + adminRole.getId());

            // Crear usuario admin si no existe
            if (userRepository.findByUsername("admin").isEmpty()) {
                System.out.println("Creando usuario admin...");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullName("Administrador");
                admin.setEmail("admin@oti.unap.edu.pe");
                admin.setEnabled(true);
                
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);
                
                User savedUser = userRepository.save(admin);
                System.out.println("Usuario admin creado con ID: " + savedUser.getId());
                System.out.println("Roles asignados: " + savedUser.getRoles().size());
            }

            // Verificar la asignación de roles
            userRepository.findByUsername("admin").ifPresent(user -> {
                System.out.println("Verificación final:");
                System.out.println("Usuario: " + user.getUsername());
                System.out.println("Roles asignados: " + user.getRoles().size());
                user.getRoles().forEach(role -> 
                    System.out.println("- " + role.getName())
                );
            });
        };
    }
}