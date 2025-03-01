package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "emprunt")
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

    @OneToMany(mappedBy = "emprunt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpruntDetail> empruntDetails = new ArrayList<>();

    public Emprunt(long id, LocalDate emprunt) {
        this.id = id;
        this.emprunt = emprunt;
    }

    public void ajouterEmpruntDetail(EmpruntDetail empruntDetail) {
        this.empruntDetails.add(empruntDetail);
    }
}