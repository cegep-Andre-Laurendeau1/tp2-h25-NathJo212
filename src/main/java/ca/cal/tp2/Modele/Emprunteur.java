package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
@DiscriminatorValue("E")
public class Emprunteur extends Utilisateur {
    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Emprunt> emprunts = new ArrayList<>();

    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Amendes> amendes = new ArrayList<>();

    public Emprunteur(long id, String prenom, String nom, String email) {
        super(id, prenom, nom, email);
    }
}