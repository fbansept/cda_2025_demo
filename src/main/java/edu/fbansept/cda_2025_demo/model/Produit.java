package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageCommande;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourClient;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourVendeur;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AffichageProduitPourClient.class})
    protected Integer id;

    @Column(nullable = false)
    @NotBlank
    @JsonView({AffichageCommande.class, AffichageProduitPourClient.class})
    protected String nom;

    @Length(min = 3, max = 10, message = "Longueur entre 3 et 10")
    @Column(unique = true, nullable = false)
    @JsonView({AffichageProduitPourClient.class})
    protected String code;

    @Column(columnDefinition = "TEXT")
    @JsonView({AffichageProduitPourClient.class})
    protected String description;

    @DecimalMin(value = "0.1")
    @JsonView({AffichageProduitPourClient.class})
    protected float prix;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView({AffichageProduitPourClient.class})
    protected Etat etat;

    @ManyToMany
    @JoinTable(
            name = "etiquette_produit",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id")
    )
    @JsonView({AffichageProduitPourClient.class})
    protected List<Etiquette> etiquettes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView({AffichageProduitPourVendeur.class})
    Vendeur createur;

    @JsonView({AffichageProduitPourClient.class})
    String nomImage;
}

