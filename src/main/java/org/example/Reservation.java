package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroReservation;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;
    
    private String statut;
    private String classe;
    private String siege;
    
    @ManyToOne
    @JoinColumn(name = "passager_id")
    private Passager passager;
    
    @ManyToOne
    @JoinColumn(name = "vol_id")
    private Vol vol;

    public Reservation(Passager passager, Vol vol, String classe, String siege) {
        this.dateReservation = new Date();
        this.statut = "En attente";
        this.passager = passager;
        this.vol = vol;
        this.classe = classe;
        this.siege = siege;
    }
    
    // Ajout manuel des getters qui posent problème
    public int getNumeroReservation() {
        return numeroReservation;
    }
    
    public Vol getVol() {
        return vol;
    }
    
    public String getClasse() {
        return classe;
    }
    
    public String getSiege() {
        return siege;
    }
    
    public Passager getPassager() {
        return passager;
    }

    public void confirmerReservation(){
        this.statut = "Confirmée";
        System.out.println("Réservation " + this.numeroReservation + " confirmée");
    }

    public void annulerReservation(){
        this.statut = "Annulée";
        System.out.println("Réservation " + this.numeroReservation + " annulée");
    }

    public void modifierReservation(String nouvelleClasse, String nouveauSiege){
        this.classe = nouvelleClasse;
        this.siege = nouveauSiege;
        System.out.println("Réservation " + this.numeroReservation + " modifiée");
    }
}
