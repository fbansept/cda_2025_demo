package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageUtilisateur;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Client extends Utilisateur {

    @JsonView(AffichageUtilisateur.class)
    String numero;

}

