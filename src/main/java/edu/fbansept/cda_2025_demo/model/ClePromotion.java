package edu.fbansept.cda_2025_demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClePromotion implements Serializable {

    @Column(name = "produit_id")
    Integer produitId;

    @Column(name = "type_promotion_id")
    Integer typePromotionId;
}

