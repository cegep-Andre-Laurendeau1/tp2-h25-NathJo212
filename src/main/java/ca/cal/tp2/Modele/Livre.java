package ca.cal.tp2.Modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livre")
@NoArgsConstructor
@Getter
@DiscriminatorValue("L")
public class Livre extends Document {

    private String auteur;
    private String editeur;
    private int nbPages;
    private final int dureeEmprunt = 3;

    public Livre(long id, String titre, int anneePublication, int nombreExemplaires, String auteur, String editeur, int nbPages) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }
}