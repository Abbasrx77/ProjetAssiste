package org.example.controllers;

import org.example.Avion;
import org.example.Vol;
import org.example.repositories.AvionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avions")
public class AvionController {

    @Autowired
    private AvionRepository avionRepository;

    
    @GetMapping
    public List<Avion> getAllAvions() {
        return avionRepository.findAll();
    }

    
    @GetMapping("/{immatriculation}")
    public ResponseEntity<Avion> getAvionByImmatriculation(@PathVariable String immatriculation) {
        Optional<Avion> avion = avionRepository.findById(immatriculation);
        return avion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @GetMapping("/modele/{modele}")
    public List<Avion> getAvionsByModele(@PathVariable String modele) {
        return avionRepository.findByModele(modele);
    }

    
    @GetMapping("/capacite")
    public List<Avion> getAvionsByCapaciteMin(@RequestParam int capaciteMin) {
        return avionRepository.findByCapaciteGreaterThanEqual(capaciteMin);
    }

    
    @PostMapping
    public ResponseEntity<Avion> createAvion(@RequestBody Avion avion) {
        Avion nouvelAvion = avionRepository.save(avion);
        return new ResponseEntity<>(nouvelAvion, HttpStatus.CREATED);
    }

    
    @PutMapping("/{immatriculation}")
    public ResponseEntity<Avion> updateAvion(@PathVariable String immatriculation, @RequestBody Avion avionDetails) {
        return avionRepository.findById(immatriculation)
                .map(avion -> {
                    avion.setModele(avionDetails.getModele());
                    avion.setCapacite(avionDetails.getCapacite());
                    Avion updatedAvion = avionRepository.save(avion);
                    return ResponseEntity.ok(updatedAvion);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{immatriculation}")
    public ResponseEntity<Void> deleteAvion(@PathVariable String immatriculation) {
        return avionRepository.findById(immatriculation)
                .map(avion -> {
                    // Nettoyer les vols associés
                    for (Vol vol : avion.getVols()) {
                        vol.setAvion(null);
                    }
                    avion.getVols().clear();
                    
                    // Supprimer l'avion
                    avionRepository.delete(avion);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 