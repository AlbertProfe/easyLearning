// backend/services/UserService.java
package dev.learn.services;

import dev.learn.data.Role;
import dev.learn.data.User;
import dev.learn.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public RegistrationResult register(String username, String password, MultipartFile file) {
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                return new RegistrationResult(false, "Username already exists");
            }

            User user = new User();
            user.setUsername(username);
            user.setHashedPassword(passwordEncoder.encode(password));
            user.setRoles(Collections.singleton(Role.USER)); // Assuming you have a Role enum
            user.setName(username); // Default name same as username

            if (file != null && !file.isEmpty()) {
                try {
                    user.setProfilePicture(file.getBytes());
                } catch (IOException e) {
                    return new RegistrationResult(false, "Failed to process avatar image");
                }
            }

            userRepository.save(user);
            return new RegistrationResult(true, null);
        } catch (Exception e) {
            return new RegistrationResult(false, "Registration failed: " + e.getMessage());
        }
    }

    @Transactional
    public void saveProfilePicture(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfilePicture(file.getBytes());
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public byte[] getProfilePicture(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getProfilePicture).orElse(null);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public static class RegistrationResult {
        private final boolean success;
        private final String error;

        public RegistrationResult(boolean success, String error) {
            this.success = success;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getError() {
            return error;
        }
    }
}