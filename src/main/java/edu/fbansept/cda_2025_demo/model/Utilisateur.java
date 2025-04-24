package edu.fbansept.cda_2025_demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.view.AffichageUtilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AffichageUtilisateur.class)
    protected Integer id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @NotBlank
    @Column(nullable = false)
    protected String password;

//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "ENUM('UTILISATEUR', 'REDACTEUR', 'ADMINISTRATEUR')")
//    protected Role role;

}

