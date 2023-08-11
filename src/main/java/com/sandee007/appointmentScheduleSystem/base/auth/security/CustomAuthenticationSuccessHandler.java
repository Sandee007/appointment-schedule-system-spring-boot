package com.sandee007.appointmentScheduleSystem.base.auth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //    https://www.baeldung.com/spring-redirect-after-login

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var userRole = authentication.getAuthorities().iterator().next().getAuthority();
        String redirectPath = this.getRedirectPath(userRole);

        redirectStrategy.sendRedirect(request, response, redirectPath);
    }

    private String getRedirectPath(String userRole) {
        ERole role = ERole.valueOf(userRole);

        return switch (role) {
            case ROLE_ADMIN -> "/admin/dashboard";
            case ROLE_CONSULTANT -> "consultant/dashboard";
            case ROLE_SEEKER -> "/";
        };
    }
}
