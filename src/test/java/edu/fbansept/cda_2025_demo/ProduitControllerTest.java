package edu.fbansept.cda_2025_demo;


import edu.fbansept.cda_2025_demo.controller.ProduitController;
import edu.fbansept.cda_2025_demo.mock.MockProduitDao;
import edu.fbansept.cda_2025_demo.mock.MockSecuriteUtils;
import edu.fbansept.cda_2025_demo.model.Produit;
import edu.fbansept.cda_2025_demo.model.Vendeur;
import edu.fbansept.cda_2025_demo.security.AppUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProduitControllerTest {

    ProduitController produitController;

    @BeforeEach
    void setUp() {
        produitController = new ProduitController(
                new MockProduitDao(), new MockSecuriteUtils("ROLE_VENDEUR")
        );
    }

    @Test
    void callGetWithExistingProduct_shouldSend200ok() {
        ResponseEntity<Produit> reponse = produitController.get(1);
        Assertions.assertEquals(HttpStatus.OK, reponse.getStatusCode());
    }

    @Test
    void callGetWithNotExistingProduct_shouldSend404notFound() {
        ResponseEntity<Produit> reponse = produitController.get(2);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, reponse.getStatusCode());
    }

    @Test
    void deleteExistingProductBySellerOwner_shouldSend204noContent() {
        Vendeur fauxVendeur = new Vendeur();
        fauxVendeur.setId(1);
        AppUserDetails userDetails = new AppUserDetails(fauxVendeur);

        ResponseEntity<Produit> reponse = produitController.delete(1, userDetails);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, reponse.getStatusCode());
    }

    @Test
    void deleteNotExistingProduct_shouldSend404notFound() {
        Vendeur fauxVendeur = new Vendeur();
        fauxVendeur.setId(1);
        AppUserDetails userDetails = new AppUserDetails(fauxVendeur);

        ResponseEntity<Produit> reponse = produitController.delete(2, userDetails);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, reponse.getStatusCode());
    }

    @Test
    void deleteExistingProductByNotSellerOwner_shouldSend403forbidden() {
        Vendeur fauxVendeur = new Vendeur();
        fauxVendeur.setId(2);
        AppUserDetails userDetails = new AppUserDetails(fauxVendeur);

        ResponseEntity<Produit> reponse = produitController.delete(1, userDetails);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, reponse.getStatusCode());
    }

    @Test
    void deleteExistingProductByNotSellerOwnerButFloorWalker_shouldSend204notContent() {

        produitController = new ProduitController(
                new MockProduitDao(), new MockSecuriteUtils("ROLE_CHEF_RAYON")
        );

        Vendeur fauxVendeur = new Vendeur();
        fauxVendeur.setId(2);
        AppUserDetails userDetails = new AppUserDetails(fauxVendeur);

        ResponseEntity<Produit> reponse = produitController.delete(1, userDetails);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, reponse.getStatusCode());
    }

}


