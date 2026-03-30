CREATE FUNCTION audit_appro() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
DECLARE
    ancien_qte      INTEGER;
    nouveau_qte     INTEGER;
    fournisseur_nom VARCHAR(50);
    produit_design  VARCHAR(80);
BEGIN
    IF tg_op = 'INSERT' THEN
        ancien_qte = 0;
        nouveau_qte = new.quantite;
        SELECT f.nom INTO fournisseur_nom FROM fournisseur f WHERE f.id = new.fournisseur_id;
        SELECT p.design INTO produit_design FROM produit p WHERE p.id = new.produit_id;
    ELSIF tg_op = 'UPDATE' THEN
        ancien_qte = old.quantite;
        nouveau_qte = new.quantite;
        SELECT f.nom INTO fournisseur_nom FROM fournisseur f WHERE f.id = new.fournisseur_id;
        SELECT p.design INTO produit_design FROM produit p WHERE p.id = new.produit_id;
    ELSIF tg_op = 'DELETE' THEN
        ancien_qte = old.quantite;
        nouveau_qte = 0;
        SELECT f.nom INTO fournisseur_nom FROM fournisseur f WHERE f.id = old.fournisseur_id;
        SELECT p.design INTO produit_design FROM produit p WHERE p.id = old.produit_id;
    END IF;

    INSERT INTO audit_approvisionnement(action, date, nom_fournisseur, produit_designation, qte_entree_ancien,
                                        qte_entree_nouveau, utilisateur)
    VALUES (tg_op,
            now(),
            fournisseur_nom,
            produit_design,
            ancien_qte,
            nouveau_qte,
            coalesce(current_setting('app.current_user_id', true), current_user));

    RETURN coalesce(new, old);
END;
$$;

CREATE TRIGGER tr_audit_approvisionnement_insert
    AFTER INSERT
    ON approvisionnement
    FOR EACH ROW
EXECUTE FUNCTION audit_appro();

CREATE TRIGGER tr_audit_approvisionnement_update
    AFTER UPDATE
    ON approvisionnement
    FOR EACH ROW
EXECUTE FUNCTION audit_appro();

CREATE TRIGGER tr_audit_approvisionnement_delete
    BEFORE DELETE
    ON approvisionnement
    FOR EACH ROW
EXECUTE FUNCTION audit_appro();

-- Replace ON DELETE CASCADE from FOREIGN KEY constraint by a trigger-based cascade delete

ALTER TABLE approvisionnement
    DROP CONSTRAINT fk_approvisionnement_produit_id;
ALTER TABLE approvisionnement
    ADD CONSTRAINT fk_approvisionnement_produit_id FOREIGN KEY (produit_id) REFERENCES produit (id);
ALTER TABLE approvisionnement
    DROP CONSTRAINT fk_approvisionnement_fournisseur_id;
ALTER TABLE approvisionnement
    ADD CONSTRAINT fk_approvisionnement_fournisseur_id FOREIGN KEY (fournisseur_id) REFERENCES fournisseur (id);

CREATE FUNCTION cascade_produit_delete() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE FROM approvisionnement WHERE approvisionnement.produit_id = OLD.id;
    RETURN OLD;
END;
$$;

CREATE TRIGGER tr_cascade_produit_delete
    BEFORE DELETE
    ON produit
    FOR EACH ROW
EXECUTE FUNCTION cascade_produit_delete();

CREATE FUNCTION cascade_fournisseur_delete() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE FROM approvisionnement WHERE approvisionnement.fournisseur_id = OLD.id;
    RETURN OLD;
END;
$$;

CREATE TRIGGER tr_cascade_fournisseur_delete
    BEFORE DELETE
    ON fournisseur
    FOR EACH ROW
EXECUTE FUNCTION cascade_fournisseur_delete();
