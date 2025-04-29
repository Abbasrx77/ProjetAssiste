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
public class PersonnelCabine extends Employe {

    private String qualification;
    
    @ManyToMany
    @JoinTable(
        name = "personnel_vol",
        joinColumns = @JoinColumn(name = "personnel_id"),
        inverseJoinColumns = @JoinColumn(name = "vol_id")
    )
    private List<Vol> vols = new ArrayList<>();

    public PersonnelCabine(
            String identifiant,
            String nom,
            String adresse,
            String contact,
            int numeroEmploye,
            Date dateEmbauche,
            String qualification) {
        super(identifiant, nom, adresse, contact, numeroEmploye, dateEmbauche);
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void affecterVol(Vol vol){
        this.vols.add(vol);
        System.out.println("Personnel de cabine " + this.getNom() + " affecté au vol " + vol.getNumeroVol());
    }

    public void ObtenirVol(){
        if (!vols.isEmpty()) {
            System.out.println("Vols actuellement affectés au personnel de cabine " + this.getNom() + " :");
            for (Vol vol : vols) {
                System.out.println("Numéro de vol : " + vol.getNumeroVol());
                System.out.println("Origine : " + vol.getOrigine());
                System.out.println("Destination : " + vol.getDestination());
                System.out.println("Date de départ : " + vol.getDateHeureDepart());
                System.out.println("Date d'arrivée : " + vol.getDateHeureArrivee());
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Aucun vol n'est actuellement affecté au personnel de cabine " + this.getNom());
        }
    }
}
