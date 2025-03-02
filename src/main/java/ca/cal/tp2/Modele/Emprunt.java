package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate emprunt;

    @ManyToOne
    @JoinColumn(name = "emprunteur_id")
    private Emprunteur emprunteur;

    @OneToMany(mappedBy = "emprunt", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<EmpruntDetail> empruntDetails = new ArrayList<>();

    public Emprunt(long id, LocalDate emprunt, Emprunteur emprunteur) {
        this.id = id;
        this.emprunt = emprunt;
        this.emprunteur = emprunteur;
    }

    public void ajouterEmpruntDetail(EmpruntDetail empruntDetail) {
        this.empruntDetails.add(empruntDetail);
    }
}