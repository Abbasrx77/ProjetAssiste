package org.example;

import java.util.Date;

public class Employe extends Personne{

    private int NumeroEmploye;
    private Date DateEmbauche;

    public Employe(String Identifiant,
                   String Nom,
                   String Adresse,
                   String Contact,
                   int NumeroEmploye,
                   Date DateEmbauche){
        super(Identifiant, Nom, Adresse, Contact);
        this.NumeroEmploye = NumeroEmploye;
        this.DateEmbauche = DateEmbauche;
    }

    public Date getDateEmbauche() {
        return DateEmbauche;
    }

    public void setDateEmbauche(Date DateEmbauche) {
        this.DateEmbauche = DateEmbauche;
    }

    public int getNumeroEmploye() {
        return NumeroEmploye;
    }

    public void setNumeroEmploye(int NumeroEmploye) {
       this.NumeroEmploye = NumeroEmploye;
    }


    public void ObtenirRole(){

    }
}