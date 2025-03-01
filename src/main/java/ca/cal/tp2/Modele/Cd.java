package ca.cal.tp2.Modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cd")
@NoArgsConstructor
@Getter
@DiscriminatorValue("C")
public class Cd extends Document {
    private String artiste;
    private int nbMinutes;
    private final int dureeEmprunt = 2;

    public Cd(long id, String titre, int anneePublication, int nombreExemplaires, String artiste, int nbMinutes) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.artiste = artiste;
        this.nbMinutes = nbMinutes;
    }
}