package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.ProduitDao;
import edu.fbansept.cda_2025_demo.dao.PromotionDao;
import edu.fbansept.cda_2025_demo.dao.TypePromotionDao;
import edu.fbansept.cda_2025_demo.model.ClePromotion;
import edu.fbansept.cda_2025_demo.model.Produit;
import edu.fbansept.cda_2025_demo.model.Promotion;
import edu.fbansept.cda_2025_demo.model.TypePromotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PromotionController {

//
//    protected PromotionDao promotionDao;

    protected PromotionDao promotionDao;
    protected ProduitDao produitDao;
    protected TypePromotionDao typePromotionDao;

    @Autowired
    public PromotionController(TypePromotionDao typePromotionDao, ProduitDao produitDao, PromotionDao promotionDao) {
        this.typePromotionDao = typePromotionDao;
        this.produitDao = produitDao;
        this.promotionDao = promotionDao;
    }

    @GetMapping("/promotion/{idProduit}/{idTypePromotion}")
    public ResponseEntity<Promotion> get(
            @PathVariable int idProduit,
            @PathVariable int idTypePromotion
    ) {

        Optional<Promotion> optionalPromotion =
                promotionDao.findById(new ClePromotion(idProduit, idTypePromotion));

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalPromotion.get(), HttpStatus.OK);

    }

    @GetMapping("/promotions")
    public List<Promotion> getAll() {

        return promotionDao.findAll();
    }

    @PostMapping("/promotion")
    public ResponseEntity<Promotion> save(@RequestBody Promotion promotion) {

        Optional<Produit> optionalProduit = produitDao.findById(promotion.getProduitId());

        if (optionalProduit.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<TypePromotion> optionalTypePromotion =
                typePromotionDao.findById(promotion.getTypePromotionId());

        if (optionalTypePromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        promotion.setTypePromotion(optionalTypePromotion.get());
        promotion.setProduit(optionalProduit.get());

        promotionDao.save(promotion);

        return new ResponseEntity<>(promotion, HttpStatus.CREATED);
    }

    @DeleteMapping("/promotion/{idProduit}/{idTypePromotion}")
    public ResponseEntity<Promotion> delete(
            @PathVariable int idProduit,
            @PathVariable int idTypePromotion
    ) {

        Optional<Promotion> optionalPromotion = promotionDao.findById(
                new ClePromotion(idProduit, idTypePromotion));

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        promotionDao.deleteById(new ClePromotion(idProduit, idTypePromotion));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/promotion/{idProduit}/{idTypePromotion}")
    public ResponseEntity<Promotion> update(
            @PathVariable int idProduit,
            @PathVariable int idTypePromotion,
            @RequestBody Promotion promotion) {

        Optional<Promotion> optionalPromotion = promotionDao.findById(
                new ClePromotion(idProduit, idTypePromotion));

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Dans le context d'une promotion on autorise uniquement le changement de la valeur
        //Si l'utilisateur veut affecter la promotion a un autre produit / type, il supprimer l'ancienne et créait une nouvelle

        optionalPromotion.get().setValeur(promotion.getValeur());

        promotionDao.save(optionalPromotion.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
