package edu.fbansept.cda_2025_demo.security;

import edu.fbansept.cda_2025_demo.model.Client;
import edu.fbansept.cda_2025_demo.model.Utilisateur;
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

        return List.of(new SimpleGrantedAuthority(isClient ? "ROLE_CLIENT" : "ROLE_VENDEUR"));
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
