INSERT INTO produit (design, stock)
VALUES ('Chaussures', 50),
       ('T-shirt', 30),
       ('Ordinateur portable', 10);

INSERT INTO fournisseur (nom)
VALUES ('Lukas André'),
       ('Marc Faniry'),
       ('Christian Mario'),
       ('Dylan Mickaël');

INSERT INTO approvisionnement (fournisseur_id, produit_id, quantite)
VALUES (1, 1, 5),
       (2, 3, 10);