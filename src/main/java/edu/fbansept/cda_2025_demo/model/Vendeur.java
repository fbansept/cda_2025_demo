package edu.fbansept.cda_2025_demo.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vendeur extends Utilisateur {

    int salaire;

    boolean chef = false;

}

