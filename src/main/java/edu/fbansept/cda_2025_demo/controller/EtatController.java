package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.EtatDao;
import edu.fbansept.cda_2025_demo.model.Etat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class EtatController {

//    @Autowired
//    protected EtatDao etatDao;

    protected EtatDao etatDao;

    @Autowired
    public EtatController(EtatDao etatDao) {
        this.etatDao = etatDao;
    }


    @GetMapping("/etat/{id}")
    public ResponseEntity<Etat> get(@PathVariable int id) {

        Optional<Etat> optionalEtat = etatDao.findById(id);

        if (optionalEtat.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalEtat.get(), HttpStatus.OK);

    }

    @GetMapping("/etats")
    public List<Etat> getAll() {

        return etatDao.findAll();
    }

    @PostMapping("/etat")
    public ResponseEntity<Etat> save(@RequestBody Etat etat) {

        etatDao.save(etat);

        return new ResponseEntity<>(etat, HttpStatus.CREATED);
    }

    @DeleteMapping("/etat/{id}")
    public ResponseEntity<Etat> delete(@PathVariable int id) {

        Optional<Etat> optionalEtat = etatDao.findById(id);

        if (optionalEtat.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        etatDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/etat/{id}")
    public ResponseEntity<Etat> update(
            @PathVariable int id,
            @RequestBody Etat etat) {

        Optional<Etat> optionalEtat = etatDao.findById(id);

        if (optionalEtat.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        etat.setId(id);

        etatDao.save(etat);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
