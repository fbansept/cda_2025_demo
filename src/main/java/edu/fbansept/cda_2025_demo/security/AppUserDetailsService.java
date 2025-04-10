package edu.fbansept.cda_2025_demo.security;

import edu.fbansept.cda_2025_demo.dao.ClientDao;
import edu.fbansept.cda_2025_demo.dao.VendeurDao;
import edu.fbansept.cda_2025_demo.model.Client;
import edu.fbansept.cda_2025_demo.model.Vendeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    protected ClientDao clientDao;
    protected VendeurDao vendeurDao;

    @Autowired
    public AppUserDetailsService(ClientDao clientDao, VendeurDao vendeurDao) {
        this.clientDao = clientDao;
        this.vendeurDao = vendeurDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Client> optionalClient = clientDao.findByEmail(email);

        if (optionalClient.isEmpty()) {

            Optional<Vendeur> optionalVendeur = vendeurDao.findByEmail(email);

            if (optionalVendeur.isEmpty()) {
                throw new UsernameNotFoundException(email);
            } else {
                return new AppUserDetails(optionalVendeur.get());
            }
        } else {
            return new AppUserDetails(optionalClient.get());
        }
    }
}
