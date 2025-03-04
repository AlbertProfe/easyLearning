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
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public RegistrationResult register(String username, String name, String password, MultipartFile file) {
        try {
            logger.debug("Checking if username '{}' exists", username);
            if (userRepository.findByUsername(username).isPresent()) {
                logger.info("Username '{}' already exists", username);
                return new RegistrationResult(false, "Username already exists");
            }

            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setHashedPassword(passwordEncoder.encode(password));
            user.setRoles(Collections.singleton(Role.USER));

            if (file != null && !file.isEmpty()) {
                logger.debug("Processing uploaded avatar for user '{}'", username);
                try {
                    user.setProfilePicture(file.getBytes());
                } catch (IOException e) {
                    logger.error("Failed to process uploaded avatar for user '{}'", username, e);
                    return new RegistrationResult(false, "Failed to process avatar image");
                }
            } else {
                logger.debug("No avatar uploaded for user '{}', using placeholder", username);
                user.setProfilePicture(getPlaceholderAvatar());
            }

            logger.debug("Saving user '{}' to database", username);
            userRepository.save(user);
            logger.info("User '{}' registered successfully", username);
            return new RegistrationResult(true, ""); // Changed null to ""
        } catch (Exception e) {
            logger.error("Registration failed for user '{}'", username, e);
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
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

    private byte[] getPlaceholderAvatar() {
        logger.debug("Loading placeholder avatar from resources");
        try (InputStream inputStream = getClass().getResourceAsStream("/static/images/placeholder-avatar.png")) {
            if (inputStream == null) {
                logger.error("Placeholder avatar file not found in resources");
                throw new IllegalStateException("Placeholder avatar file not found in resources");
            }
            byte[] avatarBytes = inputStream.readAllBytes();
            logger.debug("Successfully loaded placeholder avatar ({} bytes)", avatarBytes.length);
            return avatarBytes;
        } catch (IOException e) {
            logger.error("Failed to read placeholder avatar", e);
            throw new IllegalStateException("Failed to read placeholder avatar", e);
        }
    }

 /*   private byte[] getPlaceholderAvatar() {
        // Example: A tiny 1x1 gray pixel as a PNG (base64 encoded, converted to bytes)
        byte[] placeholder = new byte[] {
                (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x48, (byte) 0x44, (byte) 0x52,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
                (byte) 0x08, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x77, (byte) 0x53,
                (byte) 0xDE, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0C, (byte) 0x49, (byte) 0x44, (byte) 0x41,
                (byte) 0x54, (byte) 0x08, (byte) 0xD7, (byte) 0x63, (byte) 0x60, (byte) 0x60, (byte) 0x60, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x9B, (byte) 0x8F, (byte) 0x11,
                (byte) 0x7C, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x45, (byte) 0x4E,
                (byte) 0x44, (byte) 0xAE, (byte) 0x42, (byte) 0x60, (byte) 0x82
        };
        return placeholder;
    }*/
}