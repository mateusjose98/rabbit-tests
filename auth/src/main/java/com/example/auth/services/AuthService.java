package com.example.auth.services;

import com.example.auth.dtos.Dto;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    public Dto.TokenDTO login(Dto.AuthRequest request) {
        var user = findByUsername(request.username());
        validatePassword(request.password(), user.getPassword());
        var accessToken = tokenService.createToken(user.getUsername());
        return new Dto.TokenDTO(accessToken);
    }

    private void validatePassword(String rawPassword,
                                  String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("The password is incorrect!");
        }
    }

    public Dto.TokenDTO validateToken(String token) {
        validateExistingToken(token);
        var valid = tokenService.validateAccessToken(token);
        if (valid) {
            return new Dto.TokenDTO(token);
        }
        throw new RuntimeException("Invalid token!");
    }

    private void validateExistingToken(String token) {
        if (isEmpty(token)) {
            throw new ValidationException("The access token must be informed!");
        }
    }

    public Dto.AuthUserResponse getAuthenticatedUser(String token) {
        var tokenData = tokenService.getTokenData(token);
        var user = findByUsername(tokenData.username());
        return new Dto.AuthUserResponse(user.getId(), user.getUsername());
    }

    public void logout(String token) {
        tokenService.deleteRedisToken(token);
    }

    private User findByUsername(String username) {
        return repository
                .findByUsername(username)
                .orElseThrow(() -> new ValidationException("User not found!"));
    }
}