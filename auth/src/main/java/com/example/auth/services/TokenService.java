package com.example.auth.services;

import com.example.auth.dtos.Dto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class TokenService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final Long ONE_DAY_IN_SECONDS = 86400L;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public String createToken(String username) {
        var accessToken = UUID.randomUUID().toString();
        var data = new Dto.TokenData(username);
        var jsonData = getJsonData(data);
        redisTemplate.opsForValue().set(accessToken, jsonData);
        redisTemplate.expireAt(accessToken, Instant.now().plusSeconds(ONE_DAY_IN_SECONDS));
        return accessToken;
    }

    private String getJsonData(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (Exception ex) {
            return "";
        }
    }

    public Dto.TokenData getTokenData(String token) {
        var accessToken = extractToken(token);
        var jsonString = getRedisTokenValue(accessToken);
        try {
            return objectMapper.readValue(jsonString, Dto.TokenData.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error extracting the authenticated user: " + ex.getMessage());
        }
    }

    public boolean validateAccessToken(String token) {
        var accessToken = extractToken(token);
        var data = getRedisTokenValue(accessToken);
        return !isEmpty(data);
    }

    private String getRedisTokenValue(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public void deleteRedisToken(String token) {
        var accessToken = extractToken(token);
        redisTemplate.delete(accessToken);
    }

    private String extractToken(String token) {
        if (isEmpty(token)) {
            throw new ValidationException("The access token was not informed.");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }
}
