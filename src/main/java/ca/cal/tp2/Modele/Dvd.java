package ca.cal.tp2.Modele;

import lombok.Data;

@Data
public class Dvd extends Document{
    private final String realisateur;
    private final int nbMinutes;
    private final int dureeEmprunt = 1;

    public Dvd(long id, String titre, int anneePublication, int nombreExemplaires, String realisateur, int nbMinutes) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.realisateur = realisateur;
        this.nbMinutes = nbMinutes;
    }
}
