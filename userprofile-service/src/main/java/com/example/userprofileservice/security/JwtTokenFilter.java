package com.example.userprofileservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @SneakyThrows
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) {
        String bearerToken = ((HttpServletRequest) servletRequest)
                .getHeader("Authorization");
        System.out.println(bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        System.out.println("Check 1" + jwtTokenProvider.isValid(bearerToken));

        try {
            if (bearerToken != null
                    && jwtTokenProvider.isValid(bearerToken)) {
                Authentication authentication
                        = jwtTokenProvider.getAuthentication(bearerToken);
                System.out.println(authentication);
                if (authentication != null) {
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
        } catch (Exception ignored) {
            System.out.println("Our mistake");
            System.out.println(ignored);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
