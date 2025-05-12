package edu.fbansept.cda_2025_demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.dao.ProduitDao;
import edu.fbansept.cda_2025_demo.model.Etat;
import edu.fbansept.cda_2025_demo.model.Produit;
import edu.fbansept.cda_2025_demo.model.Vendeur;
import edu.fbansept.cda_2025_demo.security.AppUserDetails;
import edu.fbansept.cda_2025_demo.security.ISecuriteUtils;
import edu.fbansept.cda_2025_demo.security.IsClient;
import edu.fbansept.cda_2025_demo.security.IsVendeur;
import edu.fbansept.cda_2025_demo.service.FichierService;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourClient;
import edu.fbansept.cda_2025_demo.view.AffichageProduitPourVendeur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProduitController {

    protected ProduitDao produitDao;
    protected ISecuriteUtils securiteUtils;
    protected FichierService fichierService;

    @Autowired
    public ProduitController(ProduitDao produitDao, ISecuriteUtils securiteUtils, FichierService fichierService) {
        this.produitDao = produitDao;
        this.securiteUtils = securiteUtils;
        this.fichierService = fichierService;
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

    @GetMapping("/admin/produits")
    @IsVendeur
    @JsonView(AffichageProduitPourVendeur.class)
    public List<Produit> getAllAsVendeur() {

        return produitDao.findAll();
    }

    @GetMapping("/produits")
    @IsClient
    @JsonView(AffichageProduitPourClient.class)
    public List<Produit> getAllAsClient() {

        return produitDao.findAll();
    }

    @PostMapping("/produit")
    @IsVendeur
    public ResponseEntity<Produit> save(
            @RequestPart("produit") @Valid Produit produit,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
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

        if (photo != null) {
            try {
                fichierService.uploadToLocalFileSystem(photo, "toto.jpg");
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        produit.setCreateur(null);

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

        String role = securiteUtils.getRole(userDetails);

        //si l'id du createur du produit est different de l'id de la personne connecté
        // et que la personne n'est pas chef de rayon, alors on envoie un erreur 403 forbidden
        if (!role.equals("ROLE_CHEF_RAYON") &&
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
            @RequestBody @Valid Produit produitAsauvegarder) {

        Optional<Produit> optionalProduit = produitDao.findById(id);

        if (optionalProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //etant donnée que le formaulaire d'edition de produit ne permet pas de modifier le vendeur du produit
        //on récupère l'ancien créateur et on le réaffecte au produit à sauvegarder
        produitAsauvegarder.setCreateur(optionalProduit.get().getCreateur());

        produitAsauvegarder.setId(id);

        produitDao.save(produitAsauvegarder);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
