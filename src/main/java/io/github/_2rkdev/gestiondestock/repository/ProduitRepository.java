package io.github._2rkdev.gestiondestock.repository;

import io.github._2rkdev.gestiondestock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "produits", path = "produits")
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
