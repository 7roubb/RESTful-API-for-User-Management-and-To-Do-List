package com.cotede.todolist.security;

import com.cotede.todolist.exceptions.CustomExceptions;
import com.cotede.todolist.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws CustomExceptions.UserNotFound {
        return repository.findByEmail(username)
                .orElseThrow(() -> new CustomExceptions.UserNotFound(username));
    }
}