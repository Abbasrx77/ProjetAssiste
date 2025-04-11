package org.example;

import java.util.ArrayList;
import java.util.List;

public class Aeroport {

    private String Nom;
    private String Ville;
    private String Description;
    private List<Vol> volsDepart;
    private List<Vol> volsArrives;

    public Aeroport(String Nom,
                    String Ville,
                    String Description){
        this.Nom = Nom;
        this.Ville = Ville;
        this.Description = Description;
        this.volsDepart = new ArrayList<>();
        this.volsArrives = new ArrayList<>();
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        this.Ville = ville;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public List<Vol> getVolsDepart() {
        return volsDepart;
    }

    public void setVolsDepart(List<Vol> volsDepart) {
        this.volsDepart = volsDepart;
    }

    public List<Vol> getVolsArrives() {
        return volsArrives;
    }

    public void setVolsArrives(List<Vol> volsArrives) {
        this.volsArrives = volsArrives;
    }

    public void affecterVol(){

    }
}
