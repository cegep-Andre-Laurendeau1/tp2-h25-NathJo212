package ca.cal.tp2.Modele;

import lombok.Data;

@Data
public class Livre extends Document{
    private final String auteur;
    private final String editeur;
    private final int nbPages;
    private final int dureeEmprunt = 3;

    public Livre(long id, String titre, int anneePublication, int nombreExemplaires, String auteur, String editeur, int nbPages) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }
}
