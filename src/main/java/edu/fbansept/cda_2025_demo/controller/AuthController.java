package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.UtilisateurDao;
import edu.fbansept.cda_2025_demo.model.Utilisateur;
import edu.fbansept.cda_2025_demo.security.AppUserDetails;
import edu.fbansept.cda_2025_demo.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    protected UtilisateurDao utilisateurDao;
    protected PasswordEncoder passwordEncoder;
    protected AuthenticationProvider authenticationProvider;
    protected JwtUtils jwtUtils;

    @Autowired
    public AuthController(UtilisateurDao utilisateurDao, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, JwtUtils jwtUtils) {
        this.utilisateurDao = utilisateurDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody @Valid Utilisateur utilisateur) {

        //utilisateur.setRole(Role.UTILISATEUR);
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurDao.save(utilisateur);

        //on masque le mot de passe
        utilisateur.setPassword(null);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody @Valid Utilisateur utilisateur) {

        try {
            AppUserDetails userDetails = (AppUserDetails) authenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    utilisateur.getEmail(),
                                    utilisateur.getPassword()))
                    .getPrincipal();

            return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


    }
}
