package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.ProduitDao;
import edu.fbansept.cda_2025_demo.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProduitController {

    @Autowired
    protected ProduitDao produitDao;

    @GetMapping("/produit")
    public Produit get() {

        Produit coca = new Produit();

        coca.setNom("Coca cola");

        return coca;
    }

    @GetMapping("/produits")
    public List<Produit> getAll() {

        return produitDao.findAll();
    }

}
