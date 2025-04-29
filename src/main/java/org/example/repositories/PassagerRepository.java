package org.example.repositories;

import org.example.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, String> {
    // Méthodes spécifiques pour les recherches personnalisées
    Passager findByPasseport(String passeport);
} 