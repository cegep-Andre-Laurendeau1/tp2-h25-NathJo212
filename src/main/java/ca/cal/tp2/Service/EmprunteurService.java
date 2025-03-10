package ca.cal.tp2.Service;

import ca.cal.tp2.Exceptions.*;
import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Persistance.EmpruntRepository;
import ca.cal.tp2.Persistance.EmprunteurRepository;

import java.time.LocalDate;
import java.util.*;

public class EmprunteurService {
    private final BibliothequeService bibliothequeService;
    private final EmpruntRepository empruntRepository;
    private final EmprunteurRepository emprunteurRepository;

    public EmprunteurService(EmpruntRepository empruntRepository, BibliothequeService bibliothequeService, EmprunteurRepository emprunteurRepository) {
        this.bibliothequeService = bibliothequeService;
        this.empruntRepository = empruntRepository;
        this.emprunteurRepository = emprunteurRepository;
    }

    public void emprunterDocuments(String emprunteurEmail, List<String> documentTitres, int annee, int mois, int jour) throws DatabaseErrorExceptionHandler, EmprunteurNotFoundException, DocumentNotFoundException, PasDeCopiesException {
        Emprunteur emprunteur = emprunteurRepository.getByEmail(emprunteurEmail);
        LocalDate dateEmprunt = LocalDate.of(annee, mois, jour);

        if (emprunteur == null) {
            throw new EmprunteurNotFoundException("Emprunteur not found");
        }

        for (Amendes amende : emprunteur.getAmendes()) {
            if (!amende.isPaye()) {
                throw new RuntimeException("L'emprunteur a des amendes impayées");
            }
        }

        Emprunt emprunt = new Emprunt(0, dateEmprunt, emprunteur);
        for (String documentTitre : documentTitres) {
            Document document = bibliothequeService.getDocument(documentTitre, null, null);

            if (document == null) {
                throw new DocumentNotFoundException("Document not found");
            }

            if (empruntRepository.documentEmprunterCount(document.getId()) >= document.getNombreExemplaires()) {
                throw new PasDeCopiesException("Plus d'exemplaire empruntable pour le document, titre: " + document.getTitre());
            }

            LocalDate dateRetourPrevue = calculerDateRetour(document, dateEmprunt);
            EmpruntDetail empruntDetail = new EmpruntDetail(0, dateRetourPrevue, emprunt, document);
            emprunt.ajouterEmpruntDetail(empruntDetail);
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