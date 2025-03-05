package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@ToString
@DiscriminatorValue("E")
public class Emprunteur extends Utilisateur {
    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.PERSIST)
    private Set<Emprunt> emprunts = new HashSet<>();

    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.PERSIST)
    private Set<Amendes> amendes = new HashSet<>();

    public Emprunteur(long id, String prenom, String nom, String email) {
        super(id, prenom, nom, email);
    }
}