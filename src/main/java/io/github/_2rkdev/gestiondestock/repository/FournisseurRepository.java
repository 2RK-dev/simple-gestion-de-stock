package io.github._2rkdev.gestiondestock.repository;

import io.github._2rkdev.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "fournisseurs", path = "fournisseurs")
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}
