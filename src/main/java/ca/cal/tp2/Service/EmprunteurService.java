package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Persistance.EmpruntRepository;

import java.time.LocalDate;
import java.util.*;

public class EmprunteurService {
    private final BibliothequeService bibliothequeService;
    private final EmpruntRepository empruntRepository;

    public EmprunteurService(EmpruntRepository empruntRepository, BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
        this.empruntRepository = empruntRepository;
    }

    public void emprunterDocuments(String emprunteurEmail, List<String> documentTitres, int annee, int mois, int jour) {
        Emprunteur emprunteur = bibliothequeService.getEmprunteurParEmail(emprunteurEmail);
        LocalDate dateEmprunt = LocalDate.of(annee, mois, jour);

        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur non trouvé");
        }

        for (Amendes amende : emprunteur.getAmendes()) {
            if (!amende.isPaye()) {
                throw new RuntimeException("L'emprunteur a des amendes impayées");
            }
        }

        Emprunt emprunt = new Emprunt(0, dateEmprunt, emprunteur);
        for (String documentTitre : documentTitres) {
            for (Document document : bibliothequeService.getDocuments(documentTitre, null, null)) {

                if (document == null) {
                    throw new RuntimeException("Document non trouvé");
                }

                if (bibliothequeService.verifierQuantiteDocuments(documentTitre, null, null) <= 0) {
                    throw new RuntimeException("Plus d'exemplaire empruntable pour le document, titre: " + document.getTitre());
                }

                LocalDate dateRetourPrevue = calculerDateRetour(document, dateEmprunt);
                EmpruntDetail empruntDetail = new EmpruntDetail(0, dateRetourPrevue, emprunt, document);
                emprunt.ajouterEmpruntDetail(empruntDetail);
            }
        }

        emprunteur.getEmprunts().add(emprunt);
        empruntRepository.save(emprunt);
    }

    private LocalDate calculerDateRetour(Document document, LocalDate dateEmprunt) {
        LocalDate dateRetourPrevue = dateEmprunt;

        if (document instanceof Livre) {
            dateRetourPrevue = dateEmprunt.plusWeeks(3);
        } else if (document instanceof Cd) {
            dateRetourPrevue = dateEmprunt.plusWeeks(2);
        } else if (document instanceof Dvd) {
            dateRetourPrevue = dateEmprunt.plusWeeks(1);
        }

        return dateRetourPrevue;
    }
}