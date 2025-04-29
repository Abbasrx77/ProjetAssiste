package org.example.repositories;

import org.example.PersonnelCabine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonnelCabineRepository extends JpaRepository<PersonnelCabine, String> {
    // Méthodes spécifiques pour les recherches personnalisées
    List<PersonnelCabine> findByQualification(String qualification);
} 