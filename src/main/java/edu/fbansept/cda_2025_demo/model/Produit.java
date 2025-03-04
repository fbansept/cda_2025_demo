package edu.fbansept.cda_2025_demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Produit {

    @Id
    protected Integer id;

    @Column(nullable = false)
    protected String nom;

    @Column(length = 10, nullable = false, unique = true)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;

    protected float prix;

}

