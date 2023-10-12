package com.example.auth.dtos;

public class Dto {

    public record AuthRequest(String username, String password) {
    }

    public record AuthUserResponse(Integer id, String username) {
    }

    public record TokenDTO(String accessToken) {
    }

    public record TokenData(String username) {
    }
}
