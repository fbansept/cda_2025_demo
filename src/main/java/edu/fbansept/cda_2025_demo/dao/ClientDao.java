package edu.fbansept.cda_2025_demo.dao;

import edu.fbansept.cda_2025_demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
}
