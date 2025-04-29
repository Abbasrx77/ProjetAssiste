package org.example.controllers;

import org.example.Aeroport;
import org.example.repositories.AeroportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/aeroports")
public class AeroportController {

    @Autowired
    private AeroportRepository aeroportRepository;

    // Liste tous les aéroports
    @GetMapping
    public List<Aeroport> getAllAeroports() {
        return aeroportRepository.findAll();
    }

    // Recherche un aéroport par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Aeroport> getAeroportById(@PathVariable Long id) {
        Optional<Aeroport> aeroport = aeroportRepository.findById(id);
        return aeroport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Recherche un aéroport par son nom
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Aeroport> getAeroportByNom(@PathVariable String nom) {
        Aeroport aeroport = aeroportRepository.findByNom(nom);
        return aeroport != null ? ResponseEntity.ok(aeroport) : ResponseEntity.notFound().build();
    }

    // Recherche des aéroports par ville
    @GetMapping("/ville/{ville}")
    public List<Aeroport> getAeroportsByVille(@PathVariable String ville) {
        return aeroportRepository.findByVille(ville);
    }

    // Crée un nouvel aéroport
    @PostMapping
    public ResponseEntity<Aeroport> createAeroport(@RequestBody Aeroport aeroport) {
        Aeroport nouvelAeroport = aeroportRepository.save(aeroport);
        return new ResponseEntity<>(nouvelAeroport, HttpStatus.CREATED);
    }

    // Met à jour un aéroport existant
    @PutMapping("/{id}")
    public ResponseEntity<Aeroport> updateAeroport(@PathVariable Long id, @RequestBody Aeroport aeroportDetails) {
        return aeroportRepository.findById(id)
                .map(aeroport -> {
                    aeroport.setNom(aeroportDetails.getNom());
                    aeroport.setVille(aeroportDetails.getVille());
                    aeroport.setDescription(aeroportDetails.getDescription());
                    Aeroport updatedAeroport = aeroportRepository.save(aeroport);
                    return ResponseEntity.ok(updatedAeroport);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprime un aéroport
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAeroport(@PathVariable Long id) {
        return aeroportRepository.findById(id)
                .map(aeroport -> {
                    aeroportRepository.delete(aeroport);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 