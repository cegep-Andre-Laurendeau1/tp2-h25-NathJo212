package ca.cal.tp2.Modele;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prepose")
@NoArgsConstructor
@Getter
public class Prepose extends Utilisateur {
    public Prepose(long id, String prenom, String nom) {
        super(id, nom, prenom);
    }
}
