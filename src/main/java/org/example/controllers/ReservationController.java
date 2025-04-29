package org.example.controllers;

import org.example.Passager;
import org.example.Reservation;
import org.example.Vol;
import org.example.repositories.PassagerRepository;
import org.example.repositories.ReservationRepository;
import org.example.repositories.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private PassagerRepository passagerRepository;
    
    @Autowired
    private VolRepository volRepository;

    // Liste toutes les réservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Recherche une réservation par son numéro
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Recherche des réservations par passager
    @GetMapping("/passager/{passagerId}")
    public List<Reservation> getReservationsByPassager(@PathVariable String passagerId) {
        return reservationRepository.findByPassager_Identifiant(passagerId);
    }

    // Recherche des réservations par vol
    @GetMapping("/vol/{volId}")
    public List<Reservation> getReservationsByVol(@PathVariable String volId) {
        return reservationRepository.findByVol_NumeroVol(volId);
    }

    // Recherche des réservations par statut
    @GetMapping("/statut/{statut}")
    public List<Reservation> getReservationsByStatut(@PathVariable String statut) {
        return reservationRepository.findByStatut(statut);
    }

    // Crée une nouvelle réservation
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        Optional<Passager> passagerOpt = passagerRepository.findById(reservation.getPassager().getIdentifiant());
        Optional<Vol> volOpt = volRepository.findById(reservation.getVol().getNumeroVol());
        
        if (passagerOpt.isPresent() && volOpt.isPresent()) {
            Passager passager = passagerOpt.get();
            Vol vol = volOpt.get();
            
            if (vol.getEtat().equals("Annulé")) {
                return ResponseEntity.badRequest().body("Le vol a été annulé");
            }
            
            Reservation nouvelleReservation = new Reservation(passager, vol, reservation.getClasse(), reservation.getSiege());
            nouvelleReservation = reservationRepository.save(nouvelleReservation);
            return new ResponseEntity<>(nouvelleReservation, HttpStatus.CREATED);
        }
        
        return ResponseEntity.badRequest().body("Passager ou vol non trouvé");
    }

    // Confirme une réservation
    @PostMapping("/{id}/confirmer")
    public ResponseEntity<Void> confirmerReservation(@PathVariable int id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.confirmerReservation();
                    reservationRepository.save(reservation);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Annule une réservation
    @PostMapping("/{id}/annuler")
    public ResponseEntity<Void> annulerReservation(@PathVariable int id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.annulerReservation();
                    reservationRepository.save(reservation);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Modifie une réservation (classe et siège)
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifierReservation(
            @PathVariable int id,
            @RequestParam String nouvelleClasse,
            @RequestParam String nouveauSiege) {
        
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.modifierReservation(nouvelleClasse, nouveauSiege);
                    reservationRepository.save(reservation);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprime une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservationRepository.delete(reservation);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 