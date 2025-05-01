package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Avion {
    @Id
    private String immatriculation;
    
    private String modele;
    private int capacite;
    
    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL)
    private List<Vol> vols = new ArrayList<>();

    public Avion(String immatriculation,
                 String modele,
                 int capacite){
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.capacite = capacite;
    }

    public String getModele() {
        return modele;
    }

    public void affecterVol(Vol vol){
        if (verifierDisponibilite(vol.getDateHeureDepart(), vol.getDateHeureArrivee())) {
            this.vols.add(vol);
            vol.setAvion(this);
            System.out.println("Avion " + this.immatriculation + " affecté au vol " + vol.getNumeroVol());
        } else {
            System.out.println("L'avion " + this.immatriculation + " n'est pas disponible pour ce vol");
        }
    }

    public boolean verifierDisponibilite(Date dateDebut, Date dateFin){
        
        return vols.stream().noneMatch(vol -> 
            (dateDebut.before(vol.getDateHeureArrivee()) && dateFin.after(vol.getDateHeureDepart()))
        );
    }
}
