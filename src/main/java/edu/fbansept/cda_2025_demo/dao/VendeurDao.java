package edu.fbansept.cda_2025_demo.dao;

import edu.fbansept.cda_2025_demo.model.Vendeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendeurDao extends JpaRepository<Vendeur, Integer> {
    Optional<Vendeur> findByEmail(String email);


    @Query("FROM Vendeur v JOIN FETCH v.notifications n JOIN FETCH n.auteur a")
    List<Vendeur> findAllVendeurs();
}
