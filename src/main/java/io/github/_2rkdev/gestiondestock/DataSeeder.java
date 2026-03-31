package io.github._2rkdev.gestiondestock;

import io.github._2rkdev.gestiondestock.model.User;
import io.github._2rkdev.gestiondestock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPasswordHash(passwordEncoder.encode("password"));
            userRepository.save(user);

            User another = new User();
            another.setUsername("user");
            another.setPasswordHash(passwordEncoder.encode("password"));
            userRepository.save(another);
        }
    }
}