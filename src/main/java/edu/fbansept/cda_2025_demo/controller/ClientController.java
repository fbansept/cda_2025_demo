package edu.fbansept.cda_2025_demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda_2025_demo.dao.ClientDao;
import edu.fbansept.cda_2025_demo.model.Client;
import edu.fbansept.cda_2025_demo.view.AffichageUtilisateur;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Client", description = "Operations pertaining to clients in the system")
public class ClientController {

    protected ClientDao clientDao;

    @Autowired
    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Operation(summary = "Get a client by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the client"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/client/{id}")
    @JsonView(AffichageUtilisateur.class)
    public ResponseEntity<Client> get(@Parameter(description = "The id of the client") @PathVariable int id) {
        Optional<Client> optionalClient = clientDao.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalClient.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of clients")
    })
    @GetMapping("/clients")
    @JsonView(AffichageUtilisateur.class)
    public List<Client> getAll() {
        return clientDao.findAll();
    }

    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the client")
    })
    @PostMapping("/client")
    @JsonView(AffichageUtilisateur.class)
    public ResponseEntity<Client> save(@RequestBody @Valid Client client) {
        client.setId(null);
        clientDao.save(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a client by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the client"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/client/{id}")
    public ResponseEntity<Client> delete(@Parameter(description = "The id of the client") @PathVariable int id) {
        Optional<Client> optionalClient = clientDao.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update an existing client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the client"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/client/{id}")
    public ResponseEntity<Client> update(
            @Parameter(description = "The id of the client") @PathVariable int id,
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
