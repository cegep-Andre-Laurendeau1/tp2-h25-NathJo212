package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nom", "prenom", "email"}))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator")
@NoArgsConstructor
@Getter
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    private String prenom;
    private String email;

    public Utilisateur(long id, String prenom, String nom, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }
}