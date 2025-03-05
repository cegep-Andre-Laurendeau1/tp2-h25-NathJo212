package ca.cal.tp2.Persistance;

import ca.cal.tp2.Modele.Emprunteur;

import java.util.List;

public interface EmprunteurRepository {
    void save(Emprunteur emprunteur);
    Emprunteur getByEmail(String email);
}
