package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "emprunteur")
@NoArgsConstructor
@Getter
public class Emprunteur extends Utilisateur {
    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprunt> emprunts = new ArrayList<>();

    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Amendes> amendes = new ArrayList<>();

    public Emprunteur(long id, String prenom, String nom) {
        super(id, prenom, nom);
    }
}