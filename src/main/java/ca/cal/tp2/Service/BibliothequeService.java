package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.EmprunteurRepository;
import ca.cal.tp2.Persistance.DocumentRepository;
import ca.cal.tp2.Service.dto.DocumentDTO;
import ca.cal.tp2.Service.dto.EmprunteurDTO;

import java.util.ArrayList;
import java.util.List;

public class BibliothequeService {

    private final DocumentRepository documentRepository;
    private final EmprunteurRepository emprunteurRepository;

    public BibliothequeService(DocumentRepository documentRepository, EmprunteurRepository emprunteurRepository) {
        this.documentRepository = documentRepository;
        this.emprunteurRepository = emprunteurRepository;
    }

    public Document getDocument(String titre, String auteur, Integer annee) {
        Document document = null;
        document = documentRepository.rechercheLivre(titre, auteur, annee);
        if (document == null) {
            document = documentRepository.rechercheCd(titre, auteur);
        }
        if (document == null) {
            document = documentRepository.rechercheDvd(titre, auteur);
        }
        return document;
    }

    public DocumentDTO rechercherDocument(String titre, String auteur, Integer annee) {
        Document document = getDocument(titre, auteur, annee);
        if (document == null) {
            return null;
        }
        return DocumentDTO.toDto(document);
    }


    public EmprunteurDTO rechercherEmprunteurParEmail(String email) {
        return EmprunteurDTO.toDto(emprunteurRepository.getByEmail(email));
    }
}