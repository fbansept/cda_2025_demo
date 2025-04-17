package edu.fbansept.cda_2025_demo;


import edu.fbansept.cda_2025_demo.model.Produit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProduitTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createValidProduit_shouldBeValid() {

        Produit produitTest = new Produit();
        produitTest.setPrix(10);
        produitTest.setNom("Test");

        Set<ConstraintViolation<Produit>> violations = validator.validate(produitTest);

        assertFalse(violations.isEmpty());
    }

    @Test
    void createProduitWithoutName_shouldNotBeValid() {
        Produit produitTest = new Produit();
        produitTest.setPrix(10);
        Set<ConstraintViolation<Object>> violations = validator.validate(produitTest);

        boolean notBlankViolationExist = constraintExist(
                violations, "nom", "NotBlank");

        assertTrue(notBlankViolationExist);
    }

    @Test
    void createProduitWithNegativePrice_shouldNotBeValid() {
        Produit produitTest = new Produit();
        produitTest.setNom("test");
        produitTest.setPrix(-10);

        assertTrue(
                constraintExist(
                        validator.validate(produitTest),
                        "prix",
                        "DecimalMin"));
        
    }

    private boolean constraintExist(Set<ConstraintViolation<Object>> violations, String fieldName, String constraintName) {
        return violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals(fieldName))
                .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getName())
                .anyMatch(s -> s.equals("jakarta.validation.constraints." + constraintName));
    }


}


