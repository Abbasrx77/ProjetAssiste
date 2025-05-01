package org.example.repositories;

import org.example.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    List<Reservation> findByPassager_Identifiant(String identifiantPassager);
    List<Reservation> findByVol_NumeroVol(String numeroVol);
    List<Reservation> findByStatut(String statut);
} 