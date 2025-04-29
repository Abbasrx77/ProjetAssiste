package org.example.repositories;

import org.example.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VolRepository extends JpaRepository<Vol, String> {
    // Méthodes spécifiques pour les recherches personnalisées
    List<Vol> findByDateHeureDepartAfter(Date date);
    List<Vol> findByOrigine_Ville(String ville);
    List<Vol> findByDestination_Ville(String ville);
    List<Vol> findByEtat(String etat);
} 