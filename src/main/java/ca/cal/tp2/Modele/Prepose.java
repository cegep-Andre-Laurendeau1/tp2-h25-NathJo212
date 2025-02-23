package ca.cal.tp2.Modele;

import lombok.Data;

@Data
public class Prepose extends Utilisateur{

    public Prepose (long id, String prenom, String nom) {
        super(id, nom, prenom);
    }
}
