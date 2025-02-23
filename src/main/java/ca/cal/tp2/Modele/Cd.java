package ca.cal.tp2.Modele;

import lombok.Data;

@Data
public class Cd extends Document {
    private final String artiste;
    private final int nbMinutes;
    private final int dureeEmprunt = 2;

    public Cd(long id, String titre, int anneePublication, int nombreExemplaires, String artiste, int nbMinutes) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.artiste = artiste;
        this.nbMinutes = nbMinutes;
    }
}
