package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.LigneCommandeDao;
import edu.fbansept.cda_2025_demo.model.LigneCommande;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class LigneCommandeController {

//    @Autowired
//    protected LigneCommandeDao ligneCommandeDao;

    protected LigneCommandeDao ligneCommandeDao;

    @Autowired
    public LigneCommandeController(LigneCommandeDao ligneCommandeDao) {
        this.ligneCommandeDao = ligneCommandeDao;
    }


    @GetMapping("/ligneCommande/{id}")
    public ResponseEntity<LigneCommande> get(@PathVariable int id) {

        Optional<LigneCommande> optionalLigneCommande = ligneCommandeDao.findById(id);

        if (optionalLigneCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalLigneCommande.get(), HttpStatus.OK);

    }

    @GetMapping("/ligneCommandes")
    public List<LigneCommande> getAll() {

        return ligneCommandeDao.findAll();
    }

    @PostMapping("/ligneCommande")
    public ResponseEntity<LigneCommande> save(@RequestBody @Valid LigneCommande ligneCommande) {

        ligneCommandeDao.save(ligneCommande);

        return new ResponseEntity<>(ligneCommande, HttpStatus.CREATED);
    }

    @DeleteMapping("/ligneCommande/{id}")
    public ResponseEntity<LigneCommande> delete(@PathVariable int id) {

        Optional<LigneCommande> optionalLigneCommande = ligneCommandeDao.findById(id);

        if (optionalLigneCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ligneCommandeDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/ligneCommande/{id}")
    public ResponseEntity<LigneCommande> update(
            @PathVariable int id,
            @RequestBody @Valid LigneCommande ligneCommande) {

        Optional<LigneCommande> optionalLigneCommande = ligneCommandeDao.findById(id);

        if (optionalLigneCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ligneCommande.setId(id);

        ligneCommandeDao.save(ligneCommande);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
