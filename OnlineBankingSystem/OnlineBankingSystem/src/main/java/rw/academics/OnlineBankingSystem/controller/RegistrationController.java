package rw.academics.OnlineBankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rw.academics.OnlineBankingSystem.model.MyAppUser;
import rw.academics.OnlineBankingSystem.model.Role;
import rw.academics.OnlineBankingSystem.model.MyAppUserRepository;
import rw.academics.OnlineBankingSystem.service.NotificationService;

import java.util.HashSet;
import java.util.Set;

@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Prepare a set to hold assigned roles
        Set<Role> assignedRoles = new HashSet<>();

        // Check if the user selected a role
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            // Iterate through roles to get the role
            for (Role role : user.getRoles()) {
                if (role.equals(Role.ROLE_ADMIN)) {
                    assignedRoles.add(Role.ROLE_ADMIN); // Assign admin role
                } else {
                    assignedRoles.add(Role.ROLE_USER); // Assign default user role
                }
            }
        } else {
            // Assign default role if none is selected
            assignedRoles.add(Role.ROLE_USER);
        }

        user.setRoles(assignedRoles);

        // Save the user with their roles
        MyAppUser savedUser = myAppUserRepository.save(user);

        // Create a notification for admin
        String roleInfo = assignedRoles.toString();
        String message = "New user signed up: " + savedUser.getUsername() + ", Roles: " + roleInfo;
        notificationService.createNotification(message);

        return savedUser;
    }
}
