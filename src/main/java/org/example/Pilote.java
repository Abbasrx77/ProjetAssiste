package org.example;

import java.util.Date;

public class Pilote extends Employe {

    private String Licence;
    private double heuresDeVol;

    public Pilote(String Identifiant,
                  String Nom,
                  String Adresse,
                  String Contact,
                  int NumeroEmploye,
                  Date DateEmbauche,
                  String Licence,
                  double heuresDeVol){
        super(Identifiant, Nom, Adresse, Contact, NumeroEmploye, DateEmbauche);
        this.Licence = Licence;
        this.heuresDeVol = heuresDeVol;
    }

    public String getLicence() {
        return Licence;
    }

    public void setLicence(String Licence) {
        this.Licence = Licence;
    }

    public double getHeuresDeVol() {
        return heuresDeVol;
    }

    public void setHeuresDeVol(double HeuresDeVol) {
        this.heuresDeVol = HeuresDeVol;
    }

    public void affecterVol(){

    }

    public void obtenirVol(){

    }
}
