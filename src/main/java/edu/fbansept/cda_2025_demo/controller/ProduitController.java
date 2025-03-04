package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.ProduitDao;
import edu.fbansept.cda_2025_demo.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProduitController {

//    @Autowired
//    protected ProduitDao produitDao;

    protected ProduitDao produitDao;

    @Autowired
    public ProduitController(ProduitDao produitDao) {
        this.produitDao = produitDao;
    }


    @GetMapping("/produit/{id}")
    public ResponseEntity<Produit> get(@PathVariable int id) {

        Optional<Produit> optionalProduit = produitDao.findById(id);

        if (optionalProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalProduit.get(), HttpStatus.OK);

    }

    @GetMapping("/produits")
    public List<Produit> getAll() {

        return produitDao.findAll();
    }

}
