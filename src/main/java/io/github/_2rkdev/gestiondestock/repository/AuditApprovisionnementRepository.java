package io.github._2rkdev.gestiondestock.repository;

import io.github._2rkdev.gestiondestock.model.AuditApprovisionnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditApprovisionnementRepository extends JpaRepository<AuditApprovisionnement, Long> {
}
