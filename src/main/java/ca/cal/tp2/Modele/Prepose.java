package ca.cal.tp2.Modele;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@DiscriminatorValue("P")
public class Prepose extends Utilisateur {
    public Prepose(long id, String prenom, String nom, String email) {
        super(id, nom, prenom, email);
    }
}
