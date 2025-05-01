package org.example.repositories;

import org.example.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, String> {
    
    Passager findByPasseport(String passeport);
} 