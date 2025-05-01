package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Passager extends Personne {

    private String passeport;
    
    @JsonIgnore
    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public Passager(String identifiant,
                     String nom,
                     String adresse,
                     String contact,
                     String passeport){
        super(identifiant, nom, adresse, contact);
        this.passeport = passeport;
    }

    public void reserverVol(Vol vol, String classe, String siege){
        if (vol.getEtat().equals("Annulé")) {
            throw new IllegalStateException("Le vol a été annulé");
        }
        if (reservations.stream().anyMatch(r -> r.getVol().equals(vol))) {
            throw new IllegalStateException("Vous avez déjà une réservation pour ce vol");
        }
        Reservation reservation = new Reservation(this, vol, classe, siege);
        this.reservations.add(reservation);
        System.out.println("Réservation effectuée avec succès pour le vol " + vol.getNumeroVol());
    }

    public void annulerReservation(int numeroReservation){
        reservations.removeIf(reservation -> reservation.getNumeroReservation() == numeroReservation);
        System.out.println("Réservation " + numeroReservation + " annulée avec succès");
    }

    public void obtenirReservations(){
        System.out.println("Liste des réservations pour le passager " + this.getNom() + " :");
        for (Reservation reservation : reservations) {
            System.out.println("Numéro de réservation : " + reservation.getNumeroReservation());
            System.out.println("Vol : " + reservation.getVol().getNumeroVol());
            System.out.println("Classe : " + reservation.getClasse());
            System.out.println("Siège : " + reservation.getSiege());
            System.out.println("-------------------");
        }
    }
}
