package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourVendeur;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("VENDEUR")
public class Vendeur extends Utilisateur {

    @JsonView({AffichageProduitPourVendeur.class})
    int salaire;

    @JsonView({AffichageProduitPourVendeur.class})
    boolean chef = false;

}

