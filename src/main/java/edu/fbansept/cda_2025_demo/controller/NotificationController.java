package edu.fbansept.cda_2025_demo.controller;

import edu.fbansept.cda_2025_demo.dao.NotificationDao;
import edu.fbansept.cda_2025_demo.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class NotificationController {

//    @Autowired
//    protected NotificationDao notificationDao;

    protected NotificationDao notificationDao;

    @Autowired
    public NotificationController(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }


    @GetMapping("/notification/{id}")
    public ResponseEntity<Notification> get(@PathVariable int id) {

        Optional<Notification> optionalNotification = notificationDao.findById(id);

        if (optionalNotification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalNotification.get(), HttpStatus.OK);

    }

    @GetMapping("/notifications")
    public List<Notification> getAll() {

        return notificationDao.findAll();
    }

    @PostMapping("/notification")
    public ResponseEntity<Notification> save(@RequestBody Notification notification) {

        notificationDao.save(notification);

        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @DeleteMapping("/notification/{id}")
    public ResponseEntity<Notification> delete(@PathVariable int id) {

        Optional<Notification> optionalNotification = notificationDao.findById(id);

        if (optionalNotification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        notificationDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/notification/{id}")
    public ResponseEntity<Notification> update(
            @PathVariable int id,
            @RequestBody Notification notification) {

        Optional<Notification> optionalNotification = notificationDao.findById(id);

        if (optionalNotification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        notification.setId(id);

        notificationDao.save(notification);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
