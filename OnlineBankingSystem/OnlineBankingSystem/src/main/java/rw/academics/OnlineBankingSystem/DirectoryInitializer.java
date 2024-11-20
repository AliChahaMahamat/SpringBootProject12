package rw.academics.OnlineBankingSystem;



import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryInitializer implements CommandLineRunner {

    private final String uploadDir = "uploads/";

    @Override
    public void run(String... args) {
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs(); // This creates the directory, including any necessary but nonexistent parent directories.
            if (isCreated) {
                System.out.println("Upload directory created at: " + uploadDir);
            } else {
                System.err.println("Failed to create upload directory!");
            }
        } else {
            System.out.println("Upload directory already exists at: " + uploadDir);
        }
    }
}
