package org.example.repositories;

import org.example.PersonnelCabine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonnelCabineRepository extends JpaRepository<PersonnelCabine, String> {
   
    List<PersonnelCabine> findByQualification(String qualification);
} 