package pe.edu.unap.oti.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unap.oti.cms.model.User;
import pe.edu.unap.oti.cms.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean validatePassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public Optional<User> validateCredentials(String username, String password) {
        return findByUsername(username)
            .filter(user -> validatePassword(user, password));
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setEmail(updatedUser.getEmail());
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                existingUser.setEnabled(updatedUser.isEnabled());
                return userRepository.save(existingUser);
            });
    }
}