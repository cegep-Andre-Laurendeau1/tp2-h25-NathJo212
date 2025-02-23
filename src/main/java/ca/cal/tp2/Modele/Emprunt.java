package ca.cal.tp2.Modele;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Emprunt {
    private final long id;
    private final Date emprunt;
    private final List<EmpruntDetail> empruntDetails;

    public Emprunt(long id, Date emprunt) {
        this.id = id;
        this.emprunt = emprunt;
        this.empruntDetails = new ArrayList<>();
    }

    public void ajouterEmpruntDetail(EmpruntDetail empruntDetail) {
        this.empruntDetails.add(empruntDetail);
    }
}