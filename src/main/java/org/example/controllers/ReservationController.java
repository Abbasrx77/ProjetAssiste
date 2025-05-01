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

    // Classe DTO pour la création de réservation
    public static class ReservationDTO {
        private String passagerId;
        private String volId;
        private String classe;
        private String siege;
        private String statut;

        public String getPassagerId() {
            return passagerId;
        }

        public void setPassagerId(String passagerId) {
            this.passagerId = passagerId;
        }

        public String getVolId() {
            return volId;
        }

        public void setVolId(String volId) {
            this.volId = volId;
        }

        public String getClasse() {
            return classe;
        }

        public void setClasse(String classe) {
            this.classe = classe;
        }

        public String getSiege() {
            return siege;
        }

        public void setSiege(String siege) {
            this.siege = siege;
        }

        public String getStatut() {
            return statut;
        }

        public void setStatut(String statut) {
            this.statut = statut;
        }
    }

   
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @GetMapping("/passager/{passagerId}")
    public List<Reservation> getReservationsByPassager(@PathVariable String passagerId) {
        return reservationRepository.findByPassager_Identifiant(passagerId);
    }

    
    @GetMapping("/vol/{volId}")
    public List<Reservation> getReservationsByVol(@PathVariable String volId) {
        return reservationRepository.findByVol_NumeroVol(volId);
    }

    
    @GetMapping("/statut/{statut}")
    public List<Reservation> getReservationsByStatut(@PathVariable String statut) {
        return reservationRepository.findByStatut(statut);
    }

    
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO reservationDTO) {
        Optional<Passager> passagerOpt = passagerRepository.findById(reservationDTO.getPassagerId());
        Optional<Vol> volOpt = volRepository.findById(reservationDTO.getVolId());
        
        if (passagerOpt.isPresent() && volOpt.isPresent()) {
            Passager passager = passagerOpt.get();
            Vol vol = volOpt.get();
            
            if (vol.getEtat().equals("Annulé")) {
                return ResponseEntity.badRequest().body("Le vol a été annulé");
            }
            
            Reservation nouvelleReservation = new Reservation(passager, vol, reservationDTO.getClasse(), reservationDTO.getSiege());
            nouvelleReservation = reservationRepository.save(nouvelleReservation);
            return new ResponseEntity<>(nouvelleReservation, HttpStatus.CREATED);
        }
        
        return ResponseEntity.badRequest().body("Passager ou vol non trouvé");
    }

   
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