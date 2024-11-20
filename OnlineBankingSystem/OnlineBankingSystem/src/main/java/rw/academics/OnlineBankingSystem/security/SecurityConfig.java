package rw.academics.OnlineBankingSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import rw.academics.OnlineBankingSystem.model.MyAppUserService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyAppUserService appUserService;

    @Autowired
    public SecurityConfig(MyAppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors  // Enable CORS
                        .configurationSource(request -> {
                            var config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("http://localhost:3000"));  // Specify allowed origin
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));  // Specify allowed HTTP methods
                            config.setAllowedHeaders(List.of("*"));  // Allow all headers
                            config.setAllowCredentials(true);  // Allow cookies
                            return config;
                        })
                )
                .formLogin(httpForm -> {
                    httpForm.loginPage("/req/login").permitAll();
                    httpForm.successHandler(authenticationSuccessHandler()); // Handle success by role
                })
                .oauth2Login(oauth2Login -> {
                    oauth2Login.loginPage("/req/login")
                            .defaultSuccessUrl("http://localhost:8081/index",true)
                            .failureUrl("/req/login?error=true")
                            .userInfoEndpoint(userInfo -> userInfo
                                    .userService(customOAuth2UserService())); // Custom OAuth2UserService
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout").logoutSuccessUrl("/LandingPage").permitAll(); // Redirect to landing page on logout
                })
                .authorizeHttpRequests(registry -> {
                    // Public pages
                    registry.requestMatchers("/", "/landingpage", "/css/**", "/js/**").permitAll(); // Permit access to the landing page and static resources
                    registry.requestMatchers("/req/signup", "/forgot-password", "/index","/reset-password", "/css/**", "/js/**").permitAll();   // Add "/forgot-password" as public
                    registry.requestMatchers("/req/login?lang=*").permitAll();
                    // Admin pages restricted to ADMIN role
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    // Default authenticated access
                    registry.anyRequest().authenticated();
                })
                .build();
    }

    // Handle login success by redirecting users based on role
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    // Custom OAuth2UserService for handling social login users
    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService(); // Custom logic for OAuth2 users
    }
}
