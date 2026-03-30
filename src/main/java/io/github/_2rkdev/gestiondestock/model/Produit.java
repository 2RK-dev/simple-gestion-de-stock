package io.github._2rkdev.gestiondestock.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "design")
    @NotBlank(message = "La désignation est obligatoire")
    @Size(min = 2, max = 80, message = "La désignation doit faire entre 2 et 80 caractères")
    private String designation;

    @Column(name = "stock")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;
}
