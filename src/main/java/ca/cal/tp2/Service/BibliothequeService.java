package ca.cal.tp2.Service;

import ca.cal.tp2.Exceptions.*;
import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.EmprunteurRepository;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Service.dto.DocumentDTO;
import ca.cal.tp2.Service.dto.EmprunteurDTO;

public class BibliothequeService {

    private final DocumentRepository documentRepository;
    private final EmprunteurRepository emprunteurRepository;

    public BibliothequeService(DocumentRepository documentRepository, EmprunteurRepository emprunteurRepository) {
        this.documentRepository = documentRepository;
        this.emprunteurRepository = emprunteurRepository;
    }

    public Document getDocument(String titre, String auteur, Integer annee) throws DatabaseErrorExceptionHandler, DocumentNotFoundException {
        Document document = documentRepository.rechercheLivre(titre, auteur, annee);
        if (document == null) {
            document = documentRepository.rechercheCd(titre, auteur);
        }
        if (document == null) {
            document = documentRepository.rechercheDvd(titre, auteur);
        }
        if (document == null) {
            throw new DocumentNotFoundException("Document not found");
        }
        return document;
    }

    public DocumentDTO rechercherDocument(String titre, String auteur, Integer annee) throws DatabaseErrorExceptionHandler, DocumentNotFoundException {
        Document document = getDocument(titre, auteur, annee);
        return DocumentDTO.toDto(document);
    }

    public EmprunteurDTO rechercherEmprunteurParEmail(String email) throws DatabaseErrorExceptionHandler, EmprunteurNotFoundException {
        Emprunteur emprunteur = emprunteurRepository.getByEmail(email);
        if (emprunteur == null) {
            throw new EmprunteurNotFoundException("Emprunteur not found");
        }
        return EmprunteurDTO.toDto(emprunteur);
    }
}