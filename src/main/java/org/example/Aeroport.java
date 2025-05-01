package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Aeroport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String ville;
    private String description;
    
    @JsonIgnore
    @OneToMany(mappedBy = "origine", cascade = CascadeType.ALL)
    private List<Vol> volsDepart = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Vol> volsArrives = new ArrayList<>();

    public Aeroport(String nom,
                    String ville,
                    String description){
        this.nom = nom;
        this.ville = ville;
        this.description = description;
    }

    public void affecterVol(Vol vol, boolean estDepart){
        if (estDepart) {
            this.volsDepart.add(vol);
            vol.setOrigine(this);
            System.out.println("Vol " + vol.getNumeroVol() + " ajouté aux départs de " + this.nom);
        } else {
            this.volsArrives.add(vol);
            vol.setDestination(this);
            System.out.println("Vol " + vol.getNumeroVol() + " ajouté aux arrivées de " + this.nom);
        }
    }

    public boolean verifierCreneauDisponible(Date date, boolean estDepart){
        List<Vol> vols = estDepart ? volsDepart : volsArrives;
        return vols.stream().noneMatch(v -> 
            (date.after(v.getDateHeureDepart()) && date.before(v.getDateHeureArrivee())) ||
            date.equals(v.getDateHeureDepart()) || 
            date.equals(v.getDateHeureArrivee())
        );
    }

    @Override
    public String toString() {
        return this.nom + " (" + this.ville + ")";
    }
}
