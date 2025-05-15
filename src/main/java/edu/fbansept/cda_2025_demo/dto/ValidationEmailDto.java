package edu.fbansept.cda_2025_demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationEmailDto {

    protected String token;
    protected String email;
    //protected String password; //< -- si l'utilisateur doit choisir son mot de passe a ce moment là

}
