package edu.fbansept.cda_2025_demo.dao;

import edu.fbansept.cda_2025_demo.model.ClePromotion;
import edu.fbansept.cda_2025_demo.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDao extends JpaRepository<Promotion, ClePromotion> {
}
