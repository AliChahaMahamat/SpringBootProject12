package rw.academics.OnlineBankingSystem.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

    @GetMapping("/download-pdf")
    public ResponseEntity<Resource> downloadPDF() {
        Resource resource = new ClassPathResource("templates/about.pdf");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=about.pdf")
                .body(resource);
    }
}