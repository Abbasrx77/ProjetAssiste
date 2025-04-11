package org.example;

public class Avion {
    private String Immatriculation;
    private String Modele;
    private int Capacite;
    private Vol vol;

    public Avion(String Immatriculation,
                 String Modele,
                 int Capacite){
        this.Immatriculation = Immatriculation;
        this.Modele = Modele;
        this.Capacite = Capacite;
    }

    public String getImmatriculation() {
        return Immatriculation;
    }

    public void setImmatriculation(String Immatriculation) {
        this.Immatriculation = Immatriculation;
    }

    public String getModele() {
        return Modele;
    }

    public void setModele(String Modele) {
        this.Modele = Modele;
    }

    public int getCapacite() {
        return Capacite;
    }

    public void setCapacite(int Capacite) {
        this.Capacite = Capacite;
    }

    public void affecterVol(){

    }

    public void verifierDisponibilite(){

    }
}
