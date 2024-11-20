package rw.academics.OnlineBankingSystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.academics.OnlineBankingSystem.service.ProfilePictureService;
import rw.academics.OnlineBankingSystem.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping("/upload-profile-picture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                       @RequestParam("username") String username) {
        try {
            // Save profile picture to file system and get the path
            String profilePicturePath = profilePictureService.saveProfilePicture(file, username);

            // Update the user's profile picture path in the database
            userService.updateProfilePicturePath(username, profilePicturePath);

            return "Profile picture uploaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload profile picture!";
        }
    }
}
