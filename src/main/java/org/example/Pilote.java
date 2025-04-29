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
public class Pilote extends Employe {

    private String licence;
    private double heuresDeVol;
    
    @OneToMany(mappedBy = "pilote")
    private List<Vol> vols = new ArrayList<>();

    public Pilote(String identifiant,
                  String nom,
                  String adresse,
                  String contact,
                  int numeroEmploye,
                  Date dateEmbauche,
                  String licence,
                  double heuresDeVol){
        super(identifiant, nom, adresse, contact, numeroEmploye, dateEmbauche);
        this.licence = licence;
        this.heuresDeVol = heuresDeVol;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public double getHeuresDeVol() {
        return heuresDeVol;
    }

    public void setHeuresDeVol(double heuresDeVol) {
        this.heuresDeVol = heuresDeVol;
    }

    public void affecterVol(Vol vol){
        this.vols.add(vol);
        System.out.println("Pilote " + this.getNom() + " affecté au vol " + vol.getNumeroVol());
    }

    public void obtenirVol(){
        if (!vols.isEmpty()) {
            System.out.println("Vols actuellement affectés au pilote " + this.getNom() + " :");
            for (Vol vol : vols) {
                System.out.println("Numéro de vol : " + vol.getNumeroVol());
                System.out.println("Origine : " + vol.getOrigine());
                System.out.println("Destination : " + vol.getDestination());
                System.out.println("Date de départ : " + vol.getDateHeureDepart());
                System.out.println("Date d'arrivée : " + vol.getDateHeureArrivee());
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Aucun vol n'est actuellement affecté au pilote " + this.getNom());
        }
    }
}
