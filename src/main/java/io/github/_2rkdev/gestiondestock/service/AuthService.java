package io.github._2rkdev.gestiondestock.service;

import io.github._2rkdev.gestiondestock.api.LoginRequest;
import io.github._2rkdev.gestiondestock.api.LoginResponse;
import io.github._2rkdev.gestiondestock.configuration.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final SecurityProperties securityProperties;
    private final Clock clock = Clock.systemUTC();

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticated;
        authenticated = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        Objects.requireNonNull(authenticated.getPrincipal(), "Authenticated principal must not be null");
        UserDetails principal = (UserDetails) authenticated.getPrincipal();
        Jwt jwt = encodeAccessToken(principal.getUsername());

        return new LoginResponse(loginRequest.username(), jwt.getTokenValue());
    }

    private Jwt encodeAccessToken(String principal) {
        Instant now = Instant.now(clock);
        Instant exp = now.plus(securityProperties.jwt().accessTokenTtl());

        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer(securityProperties.jwt().issuer())
                .issuedAt(now)
                .expiresAt(exp)
                .subject(principal);

        return jwtEncoder.encode(JwtEncoderParameters.from(claims.build()));
    }
}
