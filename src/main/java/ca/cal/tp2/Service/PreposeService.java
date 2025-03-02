package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Persistance.EmpruntRepository;
import ca.cal.tp2.Persistance.EmprunteurRepository;

import java.util.List;

public class PreposeService {
    private final DocumentRepository livreRepository;
    private final DocumentRepository cdRepository;
    private final DocumentRepository dvdRepository;
    private final EmprunteurRepository emprunteurRepository;
    private final BibliothequeService bibliothequeService;

    public PreposeService(DocumentRepository livreRepository, DocumentRepository cdRepository, DocumentRepository dvdRepository, EmprunteurRepository emprunteurRepository, BibliothequeService bibliothequeService) {
        this.livreRepository = livreRepository;
        this.cdRepository = cdRepository;
        this.dvdRepository = dvdRepository;
        this.emprunteurRepository = emprunteurRepository;
        this.bibliothequeService = bibliothequeService;
    }

    public void ajouterLivre(String titre, int anneePublication, int nbExemplaires, String auteur, String editeur, int nbPages) {
        livreRepository.save(new Livre(0, titre, anneePublication, nbExemplaires, auteur, editeur, nbPages));
    }

    public void ajouterCd(String titre, int anneePublication, int nbExemplaires, String artiste, int nbMinutes) {
        cdRepository.save(new Cd(0, titre, anneePublication, nbExemplaires, artiste, nbMinutes));
    }

    public void ajouterDvd(String titre, int anneePublication, int nbExemplaires, String realisateur, int nbMinutes) {
        dvdRepository.save(new Dvd(0, titre, anneePublication, nbExemplaires, realisateur, nbMinutes));
    }

    public void ajouterEmprunteur(String nom, String prenom, String email) {
        emprunteurRepository.save(new Emprunteur(0, nom, prenom, email));
    }
}