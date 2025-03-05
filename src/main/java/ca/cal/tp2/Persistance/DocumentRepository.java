package ca.cal.tp2.Persistance;

import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Document;
import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Modele.Livre;

public interface DocumentRepository {
    void save(Document document);
    Livre rechercheLivre(String titre, String auteur, Integer annee);
    Cd rechercheCd(String titre, String artiste);
    Dvd rechercheDvd(String titre, String realisateur);
}
