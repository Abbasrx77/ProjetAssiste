package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vol {
    @Id
    private String numeroVol;
    
    @ManyToOne
    @JoinColumn(name = "origine_id")
    private Aeroport origine;
    
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Aeroport destination;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHeureDepart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHeureArrivee;
    
    private String etat;
    
    @ManyToOne
    @JoinColumn(name = "avion_id")
    private Avion avion;
    
    @ManyToOne
    @JoinColumn(name = "pilote_id")
    private Pilote pilote;
    
    @ManyToMany(mappedBy = "vols")
    private List<PersonnelCabine> personnelCabine = new ArrayList<>();
    
    @OneToMany(mappedBy = "vol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public Vol(String numeroVol,
               Aeroport origine,
               Aeroport destination,
               Date dateHeureDepart,
               Date dateHeureArrivee,
               String etat){
        this.numeroVol = numeroVol;
        this.origine = origine;
        this.destination = destination;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrivee = dateHeureArrivee;
        this.etat = etat;
    }

    public String getNumeroVol() {
        return numeroVol;
    }
    
    public String getEtat() {
        return etat;
    }
    
    public Date getDateHeureDepart() {
        return dateHeureDepart;
    }
    
    public Date getDateHeureArrivee() {
        return dateHeureArrivee;
    }
    
    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public void planifierVol(Avion avion, Pilote pilote, List<PersonnelCabine> personnelCabine){
        this.avion = avion;
        this.pilote = pilote;
        this.personnelCabine.clear();
        this.personnelCabine.addAll(personnelCabine);
        this.etat = "Planifié";
        System.out.println("Vol " + this.numeroVol + " planifié avec succès");
        System.out.println("Avion : " + avion.getModele());
        System.out.println("Pilote : " + pilote.getNom());
        System.out.println("Personnel de cabine : " + personnelCabine.size() + " membres");
    }

    public void annulerVol(){
        this.etat = "Annulé";
        for (Reservation reservation : reservations) {
            reservation.annulerReservation();
        }
        System.out.println("Vol " + this.numeroVol + " annulé");
    }

    public void modifierVol(Date nouvelleDateDepart, Date nouvelleDateArrivee){
        this.dateHeureDepart = nouvelleDateDepart;
        this.dateHeureArrivee = nouvelleDateArrivee;
        System.out.println("Vol " + this.numeroVol + " modifié");
        System.out.println("Nouvelle date de départ : " + nouvelleDateDepart);
        System.out.println("Nouvelle date d'arrivée : " + nouvelleDateArrivee);
    }

    public void ListingPassager(){
        System.out.println("Liste des passagers pour le vol " + this.numeroVol + " :");
        for (Reservation reservation : reservations) {
            Passager passager = reservation.getPassager();
            System.out.println("Passager : " + passager.getNom());
            System.out.println("Classe : " + reservation.getClasse());
            System.out.println("Siège : " + reservation.getSiege());
            System.out.println("-------------------");
        }
    }
}








