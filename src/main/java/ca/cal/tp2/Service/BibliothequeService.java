package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.EmprunteurRepository;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Service.dto.DocumentDTO;
import ca.cal.tp2.Service.dto.EmprunteurDTO;

import java.util.ArrayList;
import java.util.List;

public class BibliothequeService {

    private final DocumentRepository livreRepository;
    private final DocumentRepository cdRepository;
    private final DocumentRepository dvdRepository;
    private final EmprunteurRepository emprunteurRepository;

    public BibliothequeService(DocumentRepository livreRepository, DocumentRepository cdRepository, DocumentRepository dvdRepository, EmprunteurRepository emprunteurRepository) {
        this.livreRepository = livreRepository;
        this.cdRepository = cdRepository;
        this.dvdRepository = dvdRepository;
        this.emprunteurRepository = emprunteurRepository;
    }

    public List<Document> getDocuments(String titre, String auteur, Integer annee) {
        List<Document> resultats = new ArrayList<>();
        resultats.addAll(livreRepository.rechercheDocuments(titre, auteur, annee));
        resultats.addAll(cdRepository.rechercheDocuments(titre, auteur, annee));
        resultats.addAll(dvdRepository.rechercheDocuments(titre, auteur, annee));
        return resultats;
    }

    public List<DocumentDTO> rechercherDocuments(String titre, String auteur, Integer annee) {
        List<Document> documents = getDocuments(titre, auteur, annee);
        List<DocumentDTO> resultats = new ArrayList<>();
        for (Document document : documents) {
            resultats.add(DocumentDTO.toDto(document));
        }
        return resultats;
    }

    public Emprunteur getEmprunteurParEmail(String email) {
        return emprunteurRepository.getByEmail(email);
    }

    public EmprunteurDTO rechercherEmprunteurParEmail(String email) {
        return EmprunteurDTO.toDto(getEmprunteurParEmail(email));
    }

    public List<Emprunteur> getTousEmprunteurs() {
        return emprunteurRepository.getAll();
    }

    public int verifierQuantiteDocuments(String titre, String auteur, Integer annee) {
        List<Emprunteur> emprunteurs = getTousEmprunteurs();
        int borrowedCount = 0;

        List<Document> documents = getDocuments(titre, auteur, annee);
        if (documents.isEmpty()) {
            throw new RuntimeException("Document non trouvé: " + titre);
        }

        Document document = documents.get(0); // Assuming titles are unique
        long documentId = document.getId();

        for (Emprunteur emprunteur : emprunteurs) {
            for (Emprunt emprunt : emprunteur.getEmprunts()) {
                for (EmpruntDetail empruntDetail : emprunt.getEmpruntDetails()) {
                    if (empruntDetail.getDocument().getId() == documentId) {
                        if (empruntDetail.getDateRetourActuelle() == null) {
                            borrowedCount++;
                        }
                    }
                }
            }
        }

        int availableCount = document.getNombreExemplaires() - borrowedCount;
        if (availableCount < 0) {
            throw new RuntimeException("Quantité incorrecte pour le document: " + document.getTitre());
        }

        return availableCount;
    }
}