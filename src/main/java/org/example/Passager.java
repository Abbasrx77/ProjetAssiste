package org.example;

import java.util.ArrayList;
import java.util.List;

public class Passager extends Personne{

    private String Passeport;
    private List<Reservation> reservations;

    public Passager(String Identifiant,
                     String Nom,
                     String Adresse,
                     String Contact,
                     String Passeport){
        super(Identifiant, Nom, Adresse, Contact);
        this.Passeport = Passeport;
        this.reservations = new ArrayList<>();
    }

    public String getPasseport() {
        return Passeport;
    }

    public void setPasseport(String Passeport) {
        this.Passeport = Passeport;
    }

    private void reserverVol(){

    }

    public void annulerReservation(){

    }

    public void obtenirReservations(){

    }
}
