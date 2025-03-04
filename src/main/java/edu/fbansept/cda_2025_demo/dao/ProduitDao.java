package edu.fbansept.cda_2025_demo.dao;

import edu.fbansept.cda_2025_demo.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitDao extends JpaRepository<Produit, Integer> {


}
