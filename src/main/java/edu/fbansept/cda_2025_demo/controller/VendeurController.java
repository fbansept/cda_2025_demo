package edu.fbansept.cda_2025_demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.dao.VendeurDao;
import edu.fbansept.cda_2025_demo.model.Notification;
import edu.fbansept.cda_2025_demo.model.Vendeur;
import edu.fbansept.cda_2025_demo.view.AffichageUtilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class VendeurController {

//    @Autowired
//    protected VendeurDao vendeurDao;

    protected VendeurDao vendeurDao;

    @Autowired
    public VendeurController(VendeurDao vendeurDao) {
        this.vendeurDao = vendeurDao;
    }


    /**
     * Permet de récupérer un vendeur par son identifiant
     *
     * @param id l'identifiant unique du vendeur
     * @return une instance de ResponseEntity contenant un Vendeur avec le code 200 ou le code 404 si inexistant
     */
    @GetMapping("/vendeur/{id}")
    @JsonView(AffichageUtilisateur.class)
    public ResponseEntity<Vendeur> get(@PathVariable int id) {

        Optional<Vendeur> optionalVendeur = vendeurDao.findById(id);

        if (optionalVendeur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalVendeur.get(), HttpStatus.OK);

    }

    @GetMapping("/vendeurs")
//    @JsonView(AffichageUtilisateur.class)
    public List<Vendeur> getAll() {

        List<Vendeur> vendeurs = vendeurDao.findAll();

        //resolution du json infini manueallement (JsonView ne peut pas régler le problème)
        for (Vendeur vendeur : vendeurs) {
            for (Notification notification : vendeur.getNotifications()) {
                notification.getAuteur().setNotifications(null);
                notification.setDestinataire(null); // celui-ci peut être gérer avec un JsonView
            }
        }

        //Note :

        return vendeurs;

    }

    @PostMapping("/vendeur")
    @JsonView(AffichageUtilisateur.class)
    public ResponseEntity<Vendeur> save(@RequestBody @Valid Vendeur vendeur) {

        vendeur.setId(null);
        vendeurDao.save(vendeur);
        return new ResponseEntity<>(vendeur, HttpStatus.CREATED);
    }

    @DeleteMapping("/vendeur/{id}")
    public ResponseEntity<Vendeur> delete(@PathVariable int id) {

        Optional<Vendeur> optionalVendeur = vendeurDao.findById(id);

        if (optionalVendeur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vendeurDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/vendeur/{id}")
    public ResponseEntity<Vendeur> update(
            @PathVariable int id,
            @RequestBody @Valid Vendeur vendeur) {

        Optional<Vendeur> optionalVendeur = vendeurDao.findById(id);

        if (optionalVendeur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vendeur.setId(id);

        vendeurDao.save(vendeur);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
