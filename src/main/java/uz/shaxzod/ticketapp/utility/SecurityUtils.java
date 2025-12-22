package uz.shaxzod.ticketapp.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.shaxzod.ticketapp.models.entity.User;

import java.util.Optional;

public class SecurityUtils {
    private SecurityUtils(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static Optional<User> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")){
            return Optional.empty();
        }

        if(authentication.getPrincipal() instanceof User user){
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public static Optional<String> getCurrentUserId(){
        return getCurrentUser().map(User::getId);
    }

    public static Optional<String> getCurrentUsername(){
        return getCurrentUser().map(User::getUsername);
    }

    public static boolean hasRole(String role){
        return getCurrentUser()
                .map(user -> user.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_"+role)))
                .orElse(false);
    }

    public static boolean isAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser");
    }

}
