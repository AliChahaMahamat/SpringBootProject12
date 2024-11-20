package rw.academics.OnlineBankingSystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfilePictureService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveProfilePicture(MultipartFile file, String username) throws IOException {
        // Ensure upload directory exists
        Path directoryPath = Paths.get(uploadDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Create a unique filename using the username
        String filename = username + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(filename);

        // Save the file to the specified directory
        Files.copy(file.getInputStream(), filePath);

        // Return the file path for storing in the database
        return filePath.toString();
    }
}

