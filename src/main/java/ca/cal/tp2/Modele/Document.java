package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document")
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
public abstract class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titre;
    private int anneePublication;
    private int nombreExemplaires;

    public Document(long id, String titre, int anneePublication, int nombreExemplaires) {
        this.id = id;
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.nombreExemplaires = nombreExemplaires;
    }
}