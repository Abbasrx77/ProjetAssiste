package org.example.controllers;

import org.example.Passager;
import org.example.Vol;
import org.example.repositories.PassagerRepository;
import org.example.repositories.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passagers")
public class PassagerController {

    @Autowired
    private PassagerRepository passagerRepository;
    
    @Autowired
    private VolRepository volRepository;

    // Liste tous les passagers
    @GetMapping
    public List<Passager> getAllPassagers() {
        return passagerRepository.findAll();
    }

    // Recherche un passager par son identifiant
    @GetMapping("/{id}")
    public ResponseEntity<Passager> getPassagerById(@PathVariable String id) {
        Optional<Passager> passager = passagerRepository.findById(id);
        return passager.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crée un nouveau passager
    @PostMapping
    public ResponseEntity<Passager> createPassager(@RequestBody Passager passager) {
        Passager nouveauPassager = passagerRepository.save(passager);
        return new ResponseEntity<>(nouveauPassager, HttpStatus.CREATED);
    }

    // Met à jour un passager existant
    @PutMapping("/{id}")
    public ResponseEntity<Passager> updatePassager(@PathVariable String id, @RequestBody Passager passagerDetails) {
        return passagerRepository.findById(id)
                .map(passager -> {
                    passager.setNom(passagerDetails.getNom());
                    passager.setAdresse(passagerDetails.getAdresse());
                    passager.setContact(passagerDetails.getContact());
                    passager.setPasseport(passagerDetails.getPasseport());
                    Passager updatedPassager = passagerRepository.save(passager);
                    return ResponseEntity.ok(updatedPassager);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprime un passager
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassager(@PathVariable String id) {
        return passagerRepository.findById(id)
                .map(passager -> {
                    passagerRepository.delete(passager);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Réserve un vol pour un passager
    @PostMapping("/{passagerId}/reservations")
    public ResponseEntity<?> reserverVol(
            @PathVariable String passagerId,
            @RequestParam String volId,
            @RequestParam String classe,
            @RequestParam String siege) {
        
        Optional<Passager> passagerOpt = passagerRepository.findById(passagerId);
        Optional<Vol> volOpt = volRepository.findById(volId);
        
        if (passagerOpt.isPresent() && volOpt.isPresent()) {
            Passager passager = passagerOpt.get();
            Vol vol = volOpt.get();
            
            try {
                passager.reserverVol(vol, classe, siege);
                passagerRepository.save(passager);
                return ResponseEntity.ok().build();
            } catch (IllegalStateException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        
        return ResponseEntity.notFound().build();
    }

    // Annule une réservation
    @DeleteMapping("/{passagerId}/reservations/{reservationId}")
    public ResponseEntity<Void> annulerReservation(
            @PathVariable String passagerId,
            @PathVariable int reservationId) {
        
        return passagerRepository.findById(passagerId)
                .map(passager -> {
                    passager.annulerReservation(reservationId);
                    passagerRepository.save(passager);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 