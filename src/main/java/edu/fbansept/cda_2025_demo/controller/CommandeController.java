package edu.fbansept.cda_2025_demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.dao.CommandeDao;
import edu.fbansept.cda_2025_demo.model.Commande;
import edu.fbansept.cda_2025_demo.view.AffichageCommande;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class CommandeController {

//    @Autowired
//    protected CommandeDao commandeDao;

    protected CommandeDao commandeDao;

    @Autowired
    public CommandeController(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }


    @GetMapping("/commande/{id}")
    @JsonView(AffichageCommande.class)
    public ResponseEntity<Commande> get(@PathVariable int id) {

        Optional<Commande> optionalCommande = commandeDao.findById(id);

        if (optionalCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalCommande.get(), HttpStatus.OK);

    }

    @GetMapping("/commandes")
    @JsonView(AffichageCommande.class)
    public List<Commande> getAll() {

        return commandeDao.findAll();
    }

    @PostMapping("/commande")
    @JsonView(AffichageCommande.class)
    public ResponseEntity<Commande> save(@RequestBody @Valid Commande commande) {

        commandeDao.save(commande);

        return new ResponseEntity<>(commande, HttpStatus.CREATED);
    }

    @DeleteMapping("/commande/{id}")
    public ResponseEntity<Commande> delete(@PathVariable int id) {

        Optional<Commande> optionalCommande = commandeDao.findById(id);

        if (optionalCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commandeDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/commande/{id}")
    public ResponseEntity<Commande> update(
            @PathVariable int id,
            @RequestBody @Valid Commande commande) {

        Optional<Commande> optionalCommande = commandeDao.findById(id);

        if (optionalCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commande.setId(id);

        commandeDao.save(commande);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
