package edu.fbansept.cda_2025_demo.dao;

import edu.fbansept.cda_2025_demo.model.TypePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePromotionDao extends JpaRepository<TypePromotion, Integer> {
}
