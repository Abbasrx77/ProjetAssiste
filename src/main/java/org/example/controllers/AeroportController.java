package org.example.controllers;

import org.example.Aeroport;
import org.example.Vol;
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

    
    @GetMapping
    public List<Aeroport> getAllAeroports() {
        return aeroportRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Aeroport> getAeroportById(@PathVariable Long id) {
        Optional<Aeroport> aeroport = aeroportRepository.findById(id);
        return aeroport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Aeroport> getAeroportByNom(@PathVariable String nom) {
        Aeroport aeroport = aeroportRepository.findByNom(nom);
        return aeroport != null ? ResponseEntity.ok(aeroport) : ResponseEntity.notFound().build();
    }

    
    @GetMapping("/ville/{ville}")
    public List<Aeroport> getAeroportsByVille(@PathVariable String ville) {
        return aeroportRepository.findByVille(ville);
    }

    
    @PostMapping
    public ResponseEntity<Aeroport> createAeroport(@RequestBody Aeroport aeroport) {
        Aeroport nouvelAeroport = aeroportRepository.save(aeroport);
        return new ResponseEntity<>(nouvelAeroport, HttpStatus.CREATED);
    }

    
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

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAeroport(@PathVariable Long id) {
        return aeroportRepository.findById(id)
                .map(aeroport -> {
                    
                    for (Vol vol : aeroport.getVolsDepart()) {
                        vol.setOrigine(null);
                    }
                    aeroport.getVolsDepart().clear();
                    
                    
                    for (Vol vol : aeroport.getVolsArrives()) {
                        vol.setDestination(null);
                    }
                    aeroport.getVolsArrives().clear();
                    
                    
                    aeroportRepository.delete(aeroport);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 