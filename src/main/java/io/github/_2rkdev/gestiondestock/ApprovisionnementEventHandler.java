package io.github._2rkdev.gestiondestock;

import io.github._2rkdev.gestiondestock.model.Approvisionnement;
import io.github._2rkdev.gestiondestock.model.Produit;
import io.github._2rkdev.gestiondestock.repository.ApprovisionnementRepository;
import io.github._2rkdev.gestiondestock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class ApprovisionnementEventHandler {

    private final ProduitRepository produitRepo;
    private final ApprovisionnementRepository approRepo;

    @HandleAfterCreate
    public void handleCreate(@NonNull Approvisionnement a) {
        Produit p = a.getProduit();
        p.setStock(p.getStock() + a.getQuantite());
        produitRepo.save(p);
    }

    @HandleBeforeSave
    public void handleUpdate(@NonNull Approvisionnement a) {
        Approvisionnement old = approRepo.findById(a.getId()).orElseThrow();
        Produit p = a.getProduit();
        p.setStock(p.getStock() - old.getQuantite() + a.getQuantite());
        produitRepo.save(p);
    }

    @HandleBeforeDelete
    public void handleDelete(@NonNull Approvisionnement a) {
        Produit p = a.getProduit();
        p.setStock(p.getStock() - a.getQuantite());
        produitRepo.save(p);
    }
}
