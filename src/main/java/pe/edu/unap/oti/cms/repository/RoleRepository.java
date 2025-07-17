package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.unap.oti.cms.model.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}