package ca.cal.tp2.Persistance;

import java.util.List;

public interface DocumentRepository<T> {
    void save(T objet);
    List<T> rechercheDocuments(String titre, String auteur, Integer annee);
}
