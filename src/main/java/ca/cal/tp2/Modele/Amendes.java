package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "amendes")
@NoArgsConstructor
@Getter
public class Amendes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double montant;
    private LocalDate dateEmis;
    @Setter
    private LocalDate DatePaye;
    @Setter
    private boolean paye;

    @ManyToOne
    @JoinColumn(name = "emprunteur_id")
    private Emprunteur emprunteur;

    public Amendes(long id, double montantAmende, LocalDate dateEmis) {
        this.id = id;
        this.montant = montantAmende;
        this.dateEmis = dateEmis;
        this.paye = false;
    }
}