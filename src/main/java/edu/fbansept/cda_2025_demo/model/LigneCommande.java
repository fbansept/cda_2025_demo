package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageCommande;
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
    @JsonView(AffichageCommande.class)
    protected float prixVente;

    @Min(1)
    @JsonView(AffichageCommande.class)
    protected int quantite;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView(AffichageCommande.class)
    protected Produit produit;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Commande commande;

}

