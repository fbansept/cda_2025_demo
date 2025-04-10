package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.ClientDao;
import edu.fbansept.cda_2025_demo.model.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ClientController {

//    @Autowired
//    protected ClientDao clientDao;

    protected ClientDao clientDao;

    @Autowired
    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    @GetMapping("/client/{id}")
    public ResponseEntity<Client> get(@PathVariable int id) {

        Optional<Client> optionalClient = clientDao.findById(id);

        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalClient.get(), HttpStatus.OK);

    }

    @GetMapping("/clients")
    public List<Client> getAll() {

        return clientDao.findAll();
    }

    @PostMapping("/client")
    public ResponseEntity<Client> save(@RequestBody @Valid Client client) {

        client.setId(null);
        clientDao.save(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @DeleteMapping("/client/{id}")

    public ResponseEntity<Client> delete(@PathVariable int id) {

        Optional<Client> optionalClient = clientDao.findById(id);

        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> update(
            @PathVariable int id,
            @RequestBody @Valid Client client) {

        Optional<Client> optionalClient = clientDao.findById(id);

        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        client.setId(id);

        clientDao.save(client);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
