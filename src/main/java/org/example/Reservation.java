package org.example;

import java.util.Date;

public class Reservation {
    private int numeroReservation;
    private Date dateReservation;
    private String statut;
    private Passager passager;
    private Vol vol;

    public Reservation(int numeroReservation,
                       Date dateReservation,
                       String statut,
                       Passager passager,
                       Vol vol){
        this.numeroReservation = numeroReservation;
        this.dateReservation = dateReservation;
        this.statut = statut;
        this.passager = passager;
        this.vol = vol;
    }

    public int getNumeroReservation() {
        return numeroReservation;
    }

    public void setNumeroReservation(int numeroReservation) {
        this.numeroReservation = numeroReservation;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager passager) {
        this.passager = passager;
    }

    public void confirmerReservation(){

    }

    public void annulerReservation(){

    }

    public void modifierReservation(){

    }
}
