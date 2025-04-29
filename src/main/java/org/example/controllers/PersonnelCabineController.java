package org.example.controllers;

import org.example.PersonnelCabine;
import org.example.repositories.PersonnelCabineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personnel-cabine")
public class PersonnelCabineController {

    @Autowired
    private PersonnelCabineRepository personnelCabineRepository;

    // Liste tout le personnel de cabine
    @GetMapping
    public List<PersonnelCabine> getAllPersonnelCabine() {
        return personnelCabineRepository.findAll();
    }

    // Recherche un membre du personnel de cabine par son identifiant
    @GetMapping("/{id}")
    public ResponseEntity<PersonnelCabine> getPersonnelCabineById(@PathVariable String id) {
        Optional<PersonnelCabine> personnelCabine = personnelCabineRepository.findById(id);
        return personnelCabine.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Recherche du personnel de cabine par qualification
    @GetMapping("/qualification/{qualification}")
    public List<PersonnelCabine> getPersonnelCabineByQualification(@PathVariable String qualification) {
        return personnelCabineRepository.findByQualification(qualification);
    }

    // Crée un nouveau membre du personnel de cabine
    @PostMapping
    public ResponseEntity<PersonnelCabine> createPersonnelCabine(@RequestBody PersonnelCabine personnelCabine) {
        PersonnelCabine nouveauPersonnelCabine = personnelCabineRepository.save(personnelCabine);
        return new ResponseEntity<>(nouveauPersonnelCabine, HttpStatus.CREATED);
    }

    // Met à jour un membre du personnel de cabine existant
    @PutMapping("/{id}")
    public ResponseEntity<PersonnelCabine> updatePersonnelCabine(@PathVariable String id, @RequestBody PersonnelCabine personnelCabineDetails) {
        return personnelCabineRepository.findById(id)
                .map(personnelCabine -> {
                    personnelCabine.setNom(personnelCabineDetails.getNom());
                    personnelCabine.setAdresse(personnelCabineDetails.getAdresse());
                    personnelCabine.setContact(personnelCabineDetails.getContact());
                    personnelCabine.setNumeroEmploye(personnelCabineDetails.getNumeroEmploye());
                    personnelCabine.setDateEmbauche(personnelCabineDetails.getDateEmbauche());
                    personnelCabine.setQualification(personnelCabineDetails.getQualification());
                    PersonnelCabine updatedPersonnelCabine = personnelCabineRepository.save(personnelCabine);
                    return ResponseEntity.ok(updatedPersonnelCabine);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprime un membre du personnel de cabine
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnelCabine(@PathVariable String id) {
        return personnelCabineRepository.findById(id)
                .map(personnelCabine -> {
                    personnelCabineRepository.delete(personnelCabine);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 