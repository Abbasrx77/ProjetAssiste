package org.example;

import java.util.Date;

public class PersonnelCabine extends Employe {

    private String qualification;


    public PersonnelCabine(
            String Identifiant,
            String Nom,
            String Adresse,
            String Contact,
            int NumeroEmploye,
            Date DateEmbauche,
            String qualification) {
        super(Identifiant, Nom, Adresse, Contact, NumeroEmploye, DateEmbauche);
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void affecterVol(){

    }

    public void ObtenirVol(){

    }
}
