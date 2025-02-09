package com.cotede.todolist.security;

import com.cotede.todolist.common.ApiResponse;
import com.cotede.todolist.exceptions.CustomExceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final MessageSource messageSource;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try{
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            ApiResponse.writeSecurityErrorResponse(response, getMessage("exception.jwt.expired"), HttpStatus.UNAUTHORIZED);
        } catch (SignatureException ex) {
            ApiResponse.writeSecurityErrorResponse(response, getMessage("exception.jwt.signature"), HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException ex) {
            ApiResponse.writeSecurityErrorResponse(response, getMessage("exception.jwt.malformed"), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException ex) {
            ApiResponse.writeSecurityErrorResponse(response, getMessage("exception.jwt.unsupported"), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            ApiResponse.writeSecurityErrorResponse(response, getMessage("exception.jwt.emptyClaims"), HttpStatus.BAD_REQUEST);
        }
    }
    private String getMessage(String code){
        return messageSource.getMessage(code,new Object[]{}, LocaleContextHolder.getLocale());
    }
}
