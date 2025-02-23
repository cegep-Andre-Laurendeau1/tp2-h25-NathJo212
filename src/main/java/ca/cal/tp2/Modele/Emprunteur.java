package ca.cal.tp2.Modele;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Emprunteur extends Utilisateur{
    private final List<Emprunt> emprunts;
    private final List<Amendes> amendes;

    public Emprunteur(long id, String prenom, String nom) {
        super(id, prenom, nom);
        this.emprunts = new ArrayList<>();
        this.amendes = new ArrayList<>();
    }

}
