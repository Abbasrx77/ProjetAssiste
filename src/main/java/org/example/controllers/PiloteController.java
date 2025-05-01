package org.example.controllers;

import org.example.Pilote;
import org.example.Vol;
import org.example.repositories.PiloteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pilotes")
public class PiloteController {

    @Autowired
    private PiloteRepository piloteRepository;

    
    @GetMapping
    public List<Pilote> getAllPilotes() {
        return piloteRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Pilote> getPiloteById(@PathVariable String id) {
        Optional<Pilote> pilote = piloteRepository.findById(id);
        return pilote.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @GetMapping("/licence/{licence}")
    public List<Pilote> getPilotesByLicence(@PathVariable String licence) {
        return piloteRepository.findByLicence(licence);
    }

    
    @GetMapping("/heures")
    public List<Pilote> getPilotesByHeuresDeVolMin(@RequestParam double heuresMin) {
        return piloteRepository.findByHeuresDeVolGreaterThan(heuresMin);
    }

    
    @PostMapping
    public ResponseEntity<Pilote> createPilote(@RequestBody Pilote pilote) {
        Pilote nouveauPilote = piloteRepository.save(pilote);
        return new ResponseEntity<>(nouveauPilote, HttpStatus.CREATED);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Pilote> updatePilote(@PathVariable String id, @RequestBody Pilote piloteDetails) {
        return piloteRepository.findById(id)
                .map(pilote -> {
                    pilote.setNom(piloteDetails.getNom());
                    pilote.setAdresse(piloteDetails.getAdresse());
                    pilote.setContact(piloteDetails.getContact());
                    pilote.setNumeroEmploye(piloteDetails.getNumeroEmploye());
                    pilote.setDateEmbauche(piloteDetails.getDateEmbauche());
                    pilote.setLicence(piloteDetails.getLicence());
                    pilote.setHeuresDeVol(piloteDetails.getHeuresDeVol());
                    Pilote updatedPilote = piloteRepository.save(pilote);
                    return ResponseEntity.ok(updatedPilote);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePilote(@PathVariable String id) {
        return piloteRepository.findById(id)
                .map(pilote -> {
                    
                    for (Vol vol : pilote.getVols()) {
                        vol.setPilote(null);
                    }
                    pilote.getVols().clear();
                    
                    
                    piloteRepository.delete(pilote);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 