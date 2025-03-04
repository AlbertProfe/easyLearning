// backend/services/UserEndpoint.java
package dev.learn.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import dev.learn.data.User;
import dev.learn.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@BrowserCallable
@AnonymousAllowed
public class UserEndpoint {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private UserService userService;

    public Optional<User> getAuthenticatedUser() {
        return authenticatedUser.get();
    }

    public UserService.RegistrationResult register(String username, String password, MultipartFile avatar) {
        return userService.register(username, password, avatar);
    }

    public byte[] getUserAvatar(Long userId) {
        return userService.getProfilePicture(userId);
    }

    public void updateAvatar(Long userId, MultipartFile avatar) throws IOException {
        userService.saveProfilePicture(userId, avatar);
    }

    public User getUser(Long userId) {
        return userService.getUser(userId);
    }
}