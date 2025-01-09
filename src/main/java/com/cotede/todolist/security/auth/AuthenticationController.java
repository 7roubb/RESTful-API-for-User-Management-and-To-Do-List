package com.cotede.todolist.security.auth;

import com.cotede.todolist.common.ApiResponse;
import com.cotede.todolist.users.UserRequestDTO;
import com.cotede.todolist.users.UserResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<UserResponseDTO> register(
            @RequestBody @Valid UserRequestDTO request
    )  {
        UserResponseDTO user = service.createUser(request);
        return ApiResponse.success(user, HttpStatus.OK ,"");
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ApiResponse.success(service.authenticateUser(request),HttpStatus.OK,"Login successful");
    }


}
