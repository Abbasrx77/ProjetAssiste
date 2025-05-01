package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class Employe extends Personne {

    private int numeroEmploye;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;

    public Employe(String identifiant,
                   String nom,
                   String adresse,
                   String contact,
                   int numeroEmploye,
                   Date dateEmbauche){
        super(identifiant, nom, adresse, contact);
        this.numeroEmploye = numeroEmploye;
        this.dateEmbauche = dateEmbauche;
    }

    public void obtenirRole(){
        System.out.println("Rôle de l'employé :");
        System.out.println("Numéro d'employé : " + this.numeroEmploye);
        System.out.println("Date d'embauche : " + this.dateEmbauche);
        if (this instanceof Pilote) {
            System.out.println("Rôle : Pilote");
        } else if (this instanceof PersonnelCabine) {
            System.out.println("Rôle : Personnel de cabine");
        } else {
            System.out.println("Rôle : Employé");
        }
    }
}