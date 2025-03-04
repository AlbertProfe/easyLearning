package dev.learn.services;

import dev.learn.data.User;
import dev.learn.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For password hashing

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
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getProfilePicture();
        } else {
            return null;
        }
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // New method for user registration
    @Transactional
    public void registerUser(String username, String password, String name) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setHashedPassword(passwordEncoder.encode(password)); // Hash the password
        user.setName(name);
        userRepository.save(user);
    }
}