package ca.cal.tp2.Modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dvd")
@NoArgsConstructor
@Getter
@DiscriminatorValue("D")
public class Dvd extends Document {
    private String realisateur;
    private int nbMinutes;
    private final int dureeEmprunt = 1;

    public Dvd(long id, String titre, int anneePublication, int nombreExemplaires, String realisateur, int nbMinutes) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.realisateur = realisateur;
        this.nbMinutes = nbMinutes;
    }
}