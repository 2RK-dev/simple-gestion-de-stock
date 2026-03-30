package io.github._2rkdev.gestiondestock.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = {Approvisionnement.class})
public interface ApprovisionnementProjection {
    Long getId();

    Fournisseur getFournisseur();

    Produit getProduit();

    int getQuantite();
}
