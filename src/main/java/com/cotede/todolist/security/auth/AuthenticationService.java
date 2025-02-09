package com.cotede.todolist.security.auth;


import com.cotede.todolist.exceptions.CustomExceptions;
import org.springframework.data.redis.core.RedisTemplate;
import com.cotede.todolist.security.JwtService;
import com.cotede.todolist.users.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        return userService.createUser(userRequestDTO);
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            var claims = new HashMap<String, Object>();
            var user = ((User) auth.getPrincipal());
            claims.put("email", user.getEmail());
            claims.put("fullName", user.getFullName());

            var jwtToken = jwtService.generateToken(claims, user);
            return LoginResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new CustomExceptions.WrongPasswordOrEmail();
        } catch (Exception e) {
            throw new CustomExceptions.UserNotFound("Authentication failed.");
        }
    }


}