package org.example.repositories;

import org.example.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvionRepository extends JpaRepository<Avion, String> {
    // Méthodes spécifiques pour les recherches personnalisées
    List<Avion> findByModele(String modele);
    List<Avion> findByCapaciteGreaterThanEqual(int capaciteMinimale);
} 