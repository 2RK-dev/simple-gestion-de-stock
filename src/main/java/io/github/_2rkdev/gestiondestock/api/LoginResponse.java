package io.github._2rkdev.gestiondestock.api;

public record LoginResponse(
        String username,
        String accessToken,
        String role) {
}
