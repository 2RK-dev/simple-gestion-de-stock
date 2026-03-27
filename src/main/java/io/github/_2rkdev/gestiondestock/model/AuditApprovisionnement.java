package io.github._2rkdev.gestiondestock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "audit_approvisionnement")
public class AuditApprovisionnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Action action;

    @Column(name = "date")
    private Instant date;

    @Column(name = "nom_fournisseur")
    private String nomFournisseur;

    @Column(name = "produit_designation")
    private String produitDesignation;

    @Column(name = "qte_entree_ancien")
    private int qteEntreeAncien;

    @Column(name = "qte_entree_nouveau")
    private int qteEntreeNouveau;

    @Column(name = "utilisateur")
    private String utilisateur;

    public enum Action {
        INSERT,
        UPDATE,
        DELETE,
    }

}
