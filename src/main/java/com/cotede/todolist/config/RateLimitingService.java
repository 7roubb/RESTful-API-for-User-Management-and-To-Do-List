package com.cotede.todolist.config;
import com.cotede.todolist.exceptions.CustomExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateLimitingService {

    private final StringRedisTemplate redisTemplate;
    private static final int MAX_REQUESTS = 10;
    private static final long TIME_WINDOW_IN_MINUTES = 1;

    public void checkRequestLimit() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String key = "rate_limit:" + username;

        Long currentRequestCount = redisTemplate.opsForValue().increment(key, 1);

        Optional.ofNullable(currentRequestCount)
                .filter(count -> count == 1)
                .ifPresent(count -> redisTemplate.expire(key, Duration.ofMinutes(TIME_WINDOW_IN_MINUTES)));

        // Using Optional to throw exception if limit is exceeded
        Optional.ofNullable(currentRequestCount)
                .filter(count -> count > MAX_REQUESTS)
                .ifPresent(count -> {
                    throw new CustomExceptions.RateLimitExceededException(username);
                });
    }
    }

