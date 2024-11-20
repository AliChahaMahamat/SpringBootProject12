package rw.academics.OnlineBankingSystem.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Load the user details from the OAuth2 provider (e.g., Facebook, Instagram)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // You can extract user info here from oAuth2User, e.g., email, name, profile picture
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Custom logic: Save the user info to the database if not already exists
        // You can fetch an existing user from your DB, or create a new user if necessary

        // Return the OAuth2User object with custom processing if needed
        return oAuth2User;
    }
}
