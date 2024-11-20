package rw.academics.OnlineBankingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showLandingPage() {
        return "LandingPage"; // It automatically resolves to src/main/resources/static/landingpage.html
    }
}

