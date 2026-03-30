package io.github._2rkdev.gestiondestock;

import io.github._2rkdev.gestiondestock.model.AuditApprovisionnement;
import io.github._2rkdev.gestiondestock.repository.AuditApprovisionnementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appro-audits")
@RequiredArgsConstructor
public class ApproAuditController {
    private final AuditApprovisionnementRepository auditApprovisionnementRepository;

    @GetMapping
    public ResponseEntity<List<AuditApprovisionnement>> getAudit() {
        List<AuditApprovisionnement> all = auditApprovisionnementRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        return ResponseEntity.ok(all);
    }
}
