package ca.cal.tp2.Persistance;

import ca.cal.tp2.Exceptions.DatabaseErrorExceptionHandler;
import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Document;
import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Modele.Livre;

public interface DocumentRepository {
    void save(Document document) throws DatabaseErrorExceptionHandler;
    Livre rechercheLivre(String titre, String auteur, Integer annee) throws DatabaseErrorExceptionHandler;
    Cd rechercheCd(String titre, String artiste) throws DatabaseErrorExceptionHandler;
    Dvd rechercheDvd(String titre, String realisateur) throws DatabaseErrorExceptionHandler;
}
