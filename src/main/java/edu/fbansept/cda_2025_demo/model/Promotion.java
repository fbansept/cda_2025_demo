package edu.fbansept.cda_2025_demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ClePromotion.class)
public class Promotion {

    @Id
    protected Integer produitId;

    @Id
    protected Integer typePromotionId;

    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @MapsId("typePromotionId")
    @JoinColumn(name = "type_promotion_id")
    private TypePromotion typePromotion;

    protected Integer valeur;

}

