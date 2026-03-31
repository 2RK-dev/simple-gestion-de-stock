package io.github._2rkdev.gestiondestock;

import io.github._2rkdev.gestiondestock.model.Approvisionnement;
import io.github._2rkdev.gestiondestock.model.Produit;
import io.github._2rkdev.gestiondestock.repository.ApprovisionnementRepository;
import io.github._2rkdev.gestiondestock.repository.ProduitRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class ApprovisionnementEventHandler {

    private final ProduitRepository produitRepo;
    private final ApprovisionnementRepository approRepo;
    @PersistenceContext
    private EntityManager entityManager;

    private void setCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            if (username != null) entityManager
                    .createNativeQuery("SELECT set_config('app.current_user_id', :username, false)")
                    .setParameter("username", username)
                    .getSingleResult();
        }
    }

    @HandleAfterCreate
    @HandleAfterSave
    @HandleAfterDelete
    public void resetCurrentUserId(Approvisionnement a) {
        entityManager
                .createNativeQuery("SELECT set_config('app.current_user_id', null, false)")
                .getSingleResult();
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(@NonNull Approvisionnement ignoredA) {
        setCurrentUserId();
    }

    @HandleAfterCreate
    public void handleCreate(@NonNull Approvisionnement a) {
        Produit p = a.getProduit();
        p.setStock(p.getStock() + a.getQuantite());
        produitRepo.save(p);
    }

    @HandleBeforeSave
    public void handleUpdate(@NonNull Approvisionnement a) {
        setCurrentUserId();
        entityManager.clear();
        Approvisionnement old = approRepo.findById(a.getId()).orElseThrow();
        Produit p = a.getProduit();
        p.setStock(Math.max(0, p.getStock() - old.getQuantite() + a.getQuantite()));
        produitRepo.save(p);
    }

    @HandleBeforeDelete
    public void handleDelete(@NonNull Approvisionnement a) {
        setCurrentUserId();
        Produit p = a.getProduit();
        p.setStock(Math.max(0, p.getStock() - a.getQuantite()));
        produitRepo.save(p);
    }
}
