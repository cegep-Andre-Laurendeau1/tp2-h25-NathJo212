package ca.cal.tp2.Modele;

import lombok.Data;

@Data
public abstract class Utilisateur {
    private final long id;
    private final String nom;
    private final String prenom;

    public Utilisateur(long id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }
}
