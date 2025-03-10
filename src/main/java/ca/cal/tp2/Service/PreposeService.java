package ca.cal.tp2.Service;

import ca.cal.tp2.Exceptions.DatabaseErrorExceptionHandler;
import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Persistance.EmpruntRepository;
import ca.cal.tp2.Persistance.EmprunteurRepository;

import java.util.List;

public class PreposeService {
    private final DocumentRepository documentRepository;
    private final EmprunteurRepository emprunteurRepository;

    public PreposeService(DocumentRepository documentRepository, EmprunteurRepository emprunteurRepository) {
        this.documentRepository = documentRepository;
        this.emprunteurRepository = emprunteurRepository;
    }

    public void ajouterLivre(String titre, int anneePublication, int nbExemplaires, String auteur, String editeur, int nbPages) throws DatabaseErrorExceptionHandler {
        documentRepository.save(new Livre(0, titre.toLowerCase(), anneePublication, nbExemplaires, auteur.toLowerCase(), editeur.toLowerCase(), nbPages));
    }

    public void ajouterCd(String titre, int anneePublication, int nbExemplaires, String artiste, int nbMinutes) throws DatabaseErrorExceptionHandler {
        documentRepository.save(new Cd(0, titre.toLowerCase(), anneePublication, nbExemplaires, artiste.toLowerCase(), nbMinutes));
    }

    public void ajouterDvd(String titre, int anneePublication, int nbExemplaires, String realisateur, int nbMinutes) throws DatabaseErrorExceptionHandler {
        documentRepository.save(new Dvd(0, titre.toLowerCase(), anneePublication, nbExemplaires, realisateur.toLowerCase(), nbMinutes));
    }

    public void ajouterEmprunteur(String nom, String prenom, String email) throws DatabaseErrorExceptionHandler {
        emprunteurRepository.save(new Emprunteur(0, nom, prenom, email));
    }
}