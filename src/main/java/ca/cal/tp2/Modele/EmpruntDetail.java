package ca.cal.tp2.Modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
@Getter
public class EmpruntDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lineItemID;
    private LocalDate dateRetourPrevue;
    @Setter
    private LocalDate dateRetourActuelle;
    @Setter
    private String status;

    @ManyToOne
    @JoinColumn(name = "emprunt_id")
    private Emprunt emprunt;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    public EmpruntDetail(long lineItemID, LocalDate dateRetourPrevue, Emprunt emprunt, Document document) {
        this.lineItemID = lineItemID;
        this.dateRetourPrevue = dateRetourPrevue;
        this.status = "en cours";
        this.emprunt = emprunt;
        this.document = document;
    }
}
