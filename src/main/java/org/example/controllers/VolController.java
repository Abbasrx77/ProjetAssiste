package org.example.controllers;

import org.example.Avion;
import org.example.Pilote;
import org.example.PersonnelCabine;
import org.example.Vol;
import org.example.repositories.AvionRepository;
import org.example.repositories.PiloteRepository;
import org.example.repositories.PersonnelCabineRepository;
import org.example.repositories.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vols")
public class VolController {

    @Autowired
    private VolRepository volRepository;
    
    @Autowired
    private AvionRepository avionRepository;
    
    @Autowired
    private PiloteRepository piloteRepository;
    
    @Autowired
    private PersonnelCabineRepository personnelCabineRepository;

    // Liste tous les vols
    @GetMapping
    public List<Vol> getAllVols() {
        return volRepository.findAll();
    }

    // Recherche un vol par son numéro
    @GetMapping("/{id}")
    public ResponseEntity<Vol> getVolById(@PathVariable String id) {
        Optional<Vol> vol = volRepository.findById(id);
        return vol.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crée un nouveau vol
    @PostMapping
    public ResponseEntity<Vol> createVol(@RequestBody Vol vol) {
        Vol nouveauVol = volRepository.save(vol);
        return new ResponseEntity<>(nouveauVol, HttpStatus.CREATED);
    }

    // Met à jour un vol existant
    @PutMapping("/{id}")
    public ResponseEntity<Vol> updateVol(@PathVariable String id, @RequestBody Vol volDetails) {
        return volRepository.findById(id)
                .map(vol -> {
                    vol.setOrigine(volDetails.getOrigine());
                    vol.setDestination(volDetails.getDestination());
                    vol.setDateHeureDepart(volDetails.getDateHeureDepart());
                    vol.setDateHeureArrivee(volDetails.getDateHeureArrivee());
                    vol.setEtat(volDetails.getEtat());
                    Vol updatedVol = volRepository.save(vol);
                    return ResponseEntity.ok(updatedVol);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprime un vol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVol(@PathVariable String id) {
        return volRepository.findById(id)
                .map(vol -> {
                    volRepository.delete(vol);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Recherche les vols par ville d'origine
    @GetMapping("/depart/{ville}")
    public List<Vol> getVolsByVilleDepart(@PathVariable String ville) {
        return volRepository.findByOrigine_Ville(ville);
    }

    // Recherche les vols par ville de destination
    @GetMapping("/destination/{ville}")
    public List<Vol> getVolsByVilleDestination(@PathVariable String ville) {
        return volRepository.findByDestination_Ville(ville);
    }

    // Recherche les vols par date de départ (après une date donnée)
    @GetMapping("/date")
    public List<Vol> getVolsByDateDepart(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return volRepository.findByDateHeureDepartAfter(date);
    }

    // Recherche les vols par état
    @GetMapping("/etat/{etat}")
    public List<Vol> getVolsByEtat(@PathVariable String etat) {
        return volRepository.findByEtat(etat);
    }

    // Planifie un vol avec un avion et un équipage
    @PostMapping("/{volId}/planifier")
    public ResponseEntity<?> planifierVol(
            @PathVariable String volId,
            @RequestParam String avionId,
            @RequestParam String piloteId,
            @RequestParam List<String> personnelCabineIds) {
        
        Optional<Vol> volOpt = volRepository.findById(volId);
        Optional<Avion> avionOpt = avionRepository.findById(avionId);
        Optional<Pilote> piloteOpt = piloteRepository.findById(piloteId);
        
        if (volOpt.isPresent() && avionOpt.isPresent() && piloteOpt.isPresent()) {
            Vol vol = volOpt.get();
            Avion avion = avionOpt.get();
            Pilote pilote = piloteOpt.get();
            
            List<PersonnelCabine> personnelCabine = personnelCabineRepository.findAllById(personnelCabineIds);
            
            if (personnelCabine.isEmpty()) {
                return ResponseEntity.badRequest().body("Personnel de cabine non trouvé");
            }
            
            vol.planifierVol(avion, pilote, personnelCabine);
            avion.affecterVol(vol);
            pilote.affecterVol(vol);
            personnelCabine.forEach(pc -> pc.affecterVol(vol));
            
            volRepository.save(vol);
            avionRepository.save(avion);
            piloteRepository.save(pilote);
            personnelCabineRepository.saveAll(personnelCabine);
            
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();
    }

    // Annule un vol
    @PostMapping("/{volId}/annuler")
    public ResponseEntity<Void> annulerVol(@PathVariable String volId) {
        return volRepository.findById(volId)
                .map(vol -> {
                    vol.annulerVol();
                    volRepository.save(vol);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Modifie les dates d'un vol
    @PutMapping("/{volId}/dates")
    public ResponseEntity<Void> modifierDatesVol(
            @PathVariable String volId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date dateDepart,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date dateArrivee) {
        
        return volRepository.findById(volId)
                .map(vol -> {
                    vol.modifierVol(dateDepart, dateArrivee);
                    volRepository.save(vol);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 