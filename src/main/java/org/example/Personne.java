package org.example;

public class Personne {

    private String Identifiant;
    private String Nom;
    private String Adresse;
    private String Contact;

    public Personne(String Identifiant,
                    String Nom,
                    String Adresse,
                    String Contact){
        this.Identifiant = Identifiant;
        this.Nom = Nom;
        this.Adresse = Adresse;
        this.Contact = Contact;
    }

    public String getIdentifiant() {
        return Identifiant;
    }

    public String getNom() {
        return Nom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getContact() {
        return Contact;
    }

    public void setIdentifiant(String Identifiant) {
        this.Identifiant = Identifiant;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }


    public void ObtenirInfos(){

    }
}
