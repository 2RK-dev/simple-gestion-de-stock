package io.github._2rkdev.gestiondestock.repository;

import io.github._2rkdev.gestiondestock.model.Approvisionnement;
import io.github._2rkdev.gestiondestock.model.ApprovisionnementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "approvisionnements", path = "approvisionnements", excerptProjection = ApprovisionnementProjection.class)
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {
}
