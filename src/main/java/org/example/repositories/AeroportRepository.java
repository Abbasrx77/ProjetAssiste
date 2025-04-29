package org.example.repositories;

import org.example.Aeroport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
    // Méthodes spécifiques pour les recherches personnalisées
    Aeroport findByNom(String nom);
    List<Aeroport> findByVille(String ville);
} 