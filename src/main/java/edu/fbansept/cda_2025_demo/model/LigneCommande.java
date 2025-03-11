package edu.fbansept.cda_2025_demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @DecimalMin(value = "0.1")
    protected float prixVente;

    @Min(1)
    protected int quantite;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Produit produit;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Commande commande;

}

