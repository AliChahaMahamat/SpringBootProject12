package rw.academics.OnlineBankingSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.security.Principal;
import java.util.Locale;

@Controller
public class ContentController {

    @GetMapping("/req/login")
    public String login() {
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/index")
    public String home(Model model, Principal principal) {
        // Get the current logged-in user
        String username = principal.getName();

        // Fetch user details from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .get()
                .getAuthority();

        // Add data to the model to pass to Thymeleaf template
        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "index";
    }

    @GetMapping("/setLanguage")
    public String setLanguage(
            @RequestParam("lang") String lang,
            HttpServletRequest request,
            HttpServletResponse response) {

        // Get the LocaleResolver from the context
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

        if (localeResolver != null) {
            // Set the chosen language as the current locale
            localeResolver.setLocale(request, response, new Locale(lang));
        }

        // Redirect to the login page or any other page you want to refresh
        return "redirect:/req/login";
    }
}
