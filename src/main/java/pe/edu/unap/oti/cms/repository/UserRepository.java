package pe.edu.unap.oti.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.unap.oti.cms.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}