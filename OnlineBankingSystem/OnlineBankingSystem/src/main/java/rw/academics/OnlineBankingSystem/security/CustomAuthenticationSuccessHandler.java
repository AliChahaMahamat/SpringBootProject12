package rw.academics.OnlineBankingSystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, authentication);

        if (response.isCommitted()) {
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * This method checks if the user has selected the 'admin' checkbox in the login form
     * and if the user has the 'ROLE_ADMIN' authority.
     */
    protected String determineTargetUrl(HttpServletRequest request, Authentication authentication) {
        // Check if the "admin" checkbox is checked
        boolean loginAsAdmin = request.getParameter("admin") != null;

        // Check if the user has admin authority
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // Handle redirection based on checkbox and authority
        if (loginAsAdmin && isAdmin) {
            return "/adminUsers"; // Redirect admin to the admin page
        } else if (!loginAsAdmin && isAdmin) {
            // If the admin didn't tick the "admin" checkbox, throw an error (admin must log in as admin)
            return "/login?error=mustLoginAsAdmin";
        } else if (!isAdmin) {
            // If it's a regular user (non-admin), redirect to user home page
            return "/index";
        } else {
            // Fallback if none of the above conditions match
            return "/login?error=invalid";
        }
    }
}
