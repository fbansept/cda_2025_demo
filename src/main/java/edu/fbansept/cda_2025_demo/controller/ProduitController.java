package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.ProduitDao;
import edu.fbansept.cda_2025_demo.model.Etat;
import edu.fbansept.cda_2025_demo.model.Produit;
import edu.fbansept.cda_2025_demo.model.Vendeur;
import edu.fbansept.cda_2025_demo.security.AppUserDetails;
import edu.fbansept.cda_2025_demo.security.IsClient;
import edu.fbansept.cda_2025_demo.security.IsVendeur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
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
    @IsClient
    public ResponseEntity<Produit> get(@PathVariable int id) {

        Optional<Produit> optionalProduit = produitDao.findById(id);

        if (optionalProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalProduit.get(), HttpStatus.OK);

    }

    @GetMapping("/produits")
    @IsClient
    public List<Produit> getAll() {

        return produitDao.findAll();
    }

    @PostMapping("/produit")
    @IsVendeur
    public ResponseEntity<Produit> save(
            @RequestBody @Valid Produit produit,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        //dans le cas d'un héritage
        produit.setCreateur((Vendeur) userDetails.getUtilisateur());

        //dans le cas d'un enum
        //produit.setCreateur(userDetails.getUtilisateur());


        //si le produit recu n'a pas d'etat alors on indique qu'il neuf par defaut
        if (produit.getEtat() == null) {

            Etat etatNeuf = new Etat();
            etatNeuf.setId(1);
            produit.setEtat(etatNeuf);
        }

        produit.setId(null);
        produitDao.save(produit);
        return new ResponseEntity<>(produit, HttpStatus.CREATED);
    }

    @DeleteMapping("/produit/{id}")
    @IsVendeur
    public ResponseEntity<Produit> delete(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {

        Optional<Produit> optionalProduit = produitDao.findById(id);

        if (optionalProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String role = userDetails.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .findFirst()
                .get();

        //String role = ((SimpleGrantedAuthority)userDetails.getAuthorities().toArray()[0]).getAuthority();

        //si l'id du createur du produit est different de l'id de la prsonne connecté
        if (!role.equals("ROLE_CHEF_RAYON") ||
                optionalProduit.get().getCreateur().getId() != userDetails.getUtilisateur().getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        produitDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/produit/{id}")
    @IsVendeur
    public ResponseEntity<Produit> update(
            @PathVariable int id,
            @RequestBody @Valid Produit produit) {

        Optional<Produit> optionalProduit = produitDao.findById(id);

        if (optionalProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produit.setId(id);

        produitDao.save(produit);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
