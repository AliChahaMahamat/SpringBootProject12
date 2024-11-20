package rw.academics.OnlineBankingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.academics.OnlineBankingSystem.model.MyAppUser;
import rw.academics.OnlineBankingSystem.model.MyAppUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {

    private final MyAppUserRepository userRepository;
    private final String uploadDir = "uploads/";

    @Autowired
    public UserService(MyAppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to find all users
    public List<MyAppUser> findAllUsers() {
        return userRepository.findAll();
    }

    // Method to update profile picture using userId and uploaded file
    public void updateProfilePicture(Long userId, MultipartFile profilePicture) throws IOException {
        // Generate a unique file name based on the user ID and original file name
        String fileName = userId + "_" + profilePicture.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Save the uploaded file to the specified directory
        Files.write(filePath, profilePicture.getBytes());

        // Retrieve the user from the database
        MyAppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the path of the profile picture in the user's profile
        user.setProfilePicturePath(filePath.toString());

        // Save the updated user entity
        userRepository.save(user);
    }

    // Method to update profile picture path using username
    public void updateProfilePicturePath(String username, String profilePicturePath) {
        MyAppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfilePicturePath(profilePicturePath);
        userRepository.save(user);
    }

    // New method to get the currently authenticated user
    public MyAppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("No authenticated user found");
    }
}
