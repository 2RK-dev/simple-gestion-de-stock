package io.github._2rkdev.gestiondestock.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties (
        Cors cors,
        Jwt jwt
) {
    public record Cors(
            List<String> allowedOrigins
    ) {
    }

    public record Jwt(
            String publicKeyUri,
            String privateKeyUri,
            String issuer,
            Duration accessTokenTtl
    ) {
    }
}
