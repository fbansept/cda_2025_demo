package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageCommande;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @NotBlank
    @JsonView(AffichageCommande.class)
    protected String nom;

    @Column(updatable = false)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;

    @DecimalMin(value = "0.1")
    protected float prix;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Etat etat;

    @ManyToMany
    @JoinTable(
            name = "etiquette_produit",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id")
    )
    protected List<Etiquette> etiquettes = new ArrayList<>();

    @ManyToOne
    Vendeur createur;
}

