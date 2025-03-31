package edu.fbansept.cda_2025_demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Utilisateur {

    public interface OnUpdate {
    }

    public interface OnCreate {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @NotBlank
    @Email(groups = {OnUpdate.class, OnCreate.class})
    protected String email;

    @Column(updatable = false)
    @NotBlank(groups = {OnCreate.class})
    protected String password;
}

