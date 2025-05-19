package edu.fbansept.cda_2025_demo.security;

import edu.fbansept.cda_2025_demo.dao.UtilisateurDao;
import edu.fbansept.cda_2025_demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    protected UtilisateurDao utilisateurDao;

    @Autowired
    public AppUserDetailsService(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findByEmail(email);

        if (optionalUtilisateur.isEmpty()) {
            throw new UsernameNotFoundException(email);
        } else {
            return new AppUserDetails(optionalUtilisateur.get());
        }
    }
}
