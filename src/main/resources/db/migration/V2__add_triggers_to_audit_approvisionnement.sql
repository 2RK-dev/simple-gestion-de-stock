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