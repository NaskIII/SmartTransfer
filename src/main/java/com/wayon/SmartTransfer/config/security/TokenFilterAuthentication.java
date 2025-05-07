package com.wayon.SmartTransfer.config.security;

import com.wayon.SmartTransfer.entity.user.User;
import com.wayon.SmartTransfer.repository.IUserRepository;
import com.wayon.SmartTransfer.service.TokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class TokenFilterAuthentication extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final IUserRepository userRepository;

    public TokenFilterAuthentication(TokenService tokenService, IUserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = retrieveToken(httpServletRequest);
        boolean validToken = tokenService.isValidToken(token);

        if (validToken) {
            authenticate(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticate(String token) {
        String id = tokenService.getUserId(token);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Bad Credentials"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String retrieveToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }
}
