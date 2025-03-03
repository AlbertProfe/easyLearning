package dev.learn.services;

import dev.learn.data.User;
import dev.learn.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveProfilePicture(Long userId, MultipartFile file) throws IOException { // ID is Long now!
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfilePicture(file.getBytes()); // Get byte array from MultipartFile
            userRepository.save(user); // Use Spring Data JPA's save() method!
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public byte[] getProfilePicture(Long userId) { // ID is Long now!
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getProfilePicture();
        } else {
            return null; // Or throw an exception if a picture is always expected.
        }
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
