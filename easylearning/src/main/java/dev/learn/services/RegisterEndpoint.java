package dev.learn.services;

import com.vaadin.hilla.BrowserCallable;
import com.vaadin.flow.server.auth.AnonymousAllowed;



@AnonymousAllowed
@BrowserCallable
public class RegisterEndpoint {

    private final UserService userService;

    public RegisterEndpoint(UserService userService) {
        this.userService = userService;
    }

    public String register(String username, String password, String name) {
        try {
            userService.registerUser(username, password, name);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }
}