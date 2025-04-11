package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vol {
    private String numeroVol;
    private Aeroport Origine;
    private Aeroport Destination;
    private Date DateHeureDepart;
    private Date DateHeureArrivee;
    private String Etat;
    private Avion avion;
    private List<Reservation> reservations;

    public Vol(String numeroVol,
               Aeroport Origine,
               Aeroport Destination,
               Date DateHeureDepart,
               Date DateHeureArrivee,
               String Etat){
        this.numeroVol = numeroVol;
        this.Origine = Origine;
        this.Destination = Destination;
        this.DateHeureDepart = DateHeureDepart;
        this.DateHeureArrivee = DateHeureArrivee;
        this.Etat = Etat;
        this.reservations = new ArrayList<>();
    }

    public String getNumeroVol() {
        return numeroVol;
    }

    public void setNumeroVol(String numeroVol) {
        this.numeroVol = numeroVol;
    }

    public String getOrigine() {
        return Origine.toString();
    }

    public void setOrigine(Aeroport origine) {
        this.Origine = origine;
    }

    public Aeroport getDestination() {
        return Destination;
    }

    public void setDestination(Aeroport destination) {
        this.Destination = destination;
    }

    public Date getDateHeureDepart() {
        return DateHeureDepart;
    }

    public void setDateHeureDepart(Date dateHeureDepart) {
        this.DateHeureDepart = dateHeureDepart;
    }

    public Date getDateHeureArrivee() {
        return DateHeureArrivee;
    }

    public void setDateHeureArrivee(Date dateHeureArrivee) {
        this.DateHeureArrivee = dateHeureArrivee;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        this.Etat = etat;
    }

    public void planifierVol(){

    }

    public void annulerVol(){

    }

    public void modifierVol(){

    }

    public void ListingPassager(){

    }
}








