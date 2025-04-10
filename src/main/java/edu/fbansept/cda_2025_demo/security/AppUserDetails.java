package edu.fbansept.cda_2025_demo.security;

import edu.fbansept.cda_2025_demo.model.Client;
import edu.fbansept.cda_2025_demo.model.Utilisateur;
import edu.fbansept.cda_2025_demo.model.Vendeur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    protected Utilisateur utilisateur;

    public AppUserDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        boolean isClient = utilisateur instanceof Client;

        if (isClient) {
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        } else {
            Vendeur vendeur = (Vendeur) utilisateur;
            return List.of(new SimpleGrantedAuthority("ROLE_" + (vendeur.isChef() ? "CHEF_RAYON" : "VENDEUR")));
        }


    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }
}
