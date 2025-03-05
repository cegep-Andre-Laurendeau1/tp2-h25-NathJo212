package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate emprunt;

    @ManyToOne
    @JoinColumn(name = "emprunteur_id")
    private Emprunteur emprunteur;

    @OneToMany(mappedBy = "emprunt", cascade = CascadeType.PERSIST)
    private Set<EmpruntDetail> empruntDetails = new HashSet<>();

    public Emprunt(long id, LocalDate emprunt, Emprunteur emprunteur) {
        this.id = id;
        this.emprunt = emprunt;
        this.emprunteur = emprunteur;
    }

    public void ajouterEmpruntDetail(EmpruntDetail empruntDetail) {
        this.empruntDetails.add(empruntDetail);
    }
}