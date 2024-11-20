package rw.academics.OnlineBankingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/services")
    public String services() {
        return "services"; // Create services.html similarly if needed
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // Create contact.html similarly if needed
    }
}
