package org.example.repositories;

import org.example.Pilote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PiloteRepository extends JpaRepository<Pilote, String> {
    // Méthodes spécifiques pour les recherches personnalisées
    List<Pilote> findByLicence(String licence);
    List<Pilote> findByHeuresDeVolGreaterThan(double heuresMinimales);
} 