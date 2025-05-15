package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.UtilisateurDao;
import edu.fbansept.cda_2025_demo.dto.ValidationEmailDto;
import edu.fbansept.cda_2025_demo.model.Utilisateur;
import edu.fbansept.cda_2025_demo.security.AppUserDetails;
import edu.fbansept.cda_2025_demo.security.ISecuriteUtils;
import edu.fbansept.cda_2025_demo.service.EmailService;
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

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin
@RestController
public class AuthController {

    protected UtilisateurDao utilisateurDao;
    protected PasswordEncoder passwordEncoder;
    protected AuthenticationProvider authenticationProvider;
    protected ISecuriteUtils securiteUtils;
    protected EmailService emailService;

    @Autowired
    public AuthController(UtilisateurDao utilisateurDao, PasswordEncoder passwordEncoder,
                          AuthenticationProvider authenticationProvider, ISecuriteUtils securiteUtils, EmailService emailService) {
        this.utilisateurDao = utilisateurDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.securiteUtils = securiteUtils;
        this.emailService = emailService;
    }


    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody @Valid Utilisateur utilisateur) throws IOException {

        //utilisateur.setRole(Role.UTILISATEUR);
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        String tokenValidationEmail = UUID.randomUUID().toString();

        utilisateur.setJetonVerificationEmail(tokenValidationEmail);
        utilisateurDao.save(utilisateur);

        emailService.sendEmailValidationToken(utilisateur.getEmail(), tokenValidationEmail);

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

            return new ResponseEntity<>(securiteUtils.generateToken(userDetails), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/validate-email")
    public ResponseEntity<Utilisateur> validateEmail(@RequestBody ValidationEmailDto validationEmailDto) {

        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(validationEmailDto.getEmail());

        if (utilisateur.get().getJetonVerificationEmail().equals(validationEmailDto.getToken())) {
            utilisateur.get().setJetonVerificationEmail(null);
            utilisateurDao.save(utilisateur.get());
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
