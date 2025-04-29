package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Personne {

    @Id
    private String identifiant;
    
    private String nom;
    private String adresse;
    private String contact;

    public Personne(String identifiant,
                    String nom,
                    String adresse,
                    String contact){
        this.identifiant = identifiant;
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
    }

    public String getNom() {
        return nom;
    }

    public void obtenirInfos(){
        System.out.println("Informations de la personne :");
        System.out.println("Identifiant : " + this.identifiant);
        System.out.println("Nom : " + this.nom);
        System.out.println("Adresse : " + this.adresse);
        System.out.println("Contact : " + this.contact);
    }
}
