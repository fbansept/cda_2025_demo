package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourClient;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourVendeur;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Etiquette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AffichageProduitPourVendeur.class})
    protected Integer id;

    @Column(nullable = false)
    @JsonView({AffichageProduitPourClient.class})
    protected String nom;

    @Column(nullable = false)
    @JsonView({AffichageProduitPourClient.class})
    protected String couleur;


}

