package io.github._2rkdev.gestiondestock.repository;

import io.github._2rkdev.gestiondestock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
