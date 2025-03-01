package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.JDBC.BibliothequeRepositoryJDBC;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import ca.cal.tp2.Service.dto.DocumentDTO;
import ca.cal.tp2.Service.dto.LivreDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibliothequeService {

    private final PersistanceGenerique persistanceGenerique;
    private final Map<Long, Emprunteur> emprunteurs;

    public BibliothequeService(PersistanceGenerique persistanceGenerique) {
        this.persistanceGenerique = persistanceGenerique;
        this.emprunteurs = new HashMap<>();
    }

    public void ajouterEmprunteur(Emprunteur emprunteur){
        emprunteurs.put(emprunteur.getId(), emprunteur);
    }

    public Document getDocumentParId(long documentId) {
        return (Document) persistanceGenerique.getById(documentId);
    }

    public DocumentDTO rechercherDocumentParId(long documentId) {
        return DocumentDTO.toDto((Document) persistanceGenerique.getById(documentId));
    }

    public Emprunteur getEmprunteurParId(long idEmprunteur) {
        return emprunteurs.get(idEmprunteur);
    }

    public List<Emprunteur> getTousEmprunteurs() {
        return new ArrayList<>(emprunteurs.values());
    }

    /*
    public List<Document> rechercherDocuments(String titre, String auteur, String editeur, String type) {
        List<Document> resultats = new ArrayList<>();
        for (Document document : documents.values()) {
            boolean correspond = true;
            if (titre != null && !document.getTitre().equalsIgnoreCase(titre)) {
                correspond = false;
            }
            if (auteur != null && document instanceof Livre && !((Livre) document).getAuteur().equalsIgnoreCase(auteur)) {
                correspond = false;
            }
            if (editeur != null && document instanceof Livre && !((Livre) document).getEditeur().equalsIgnoreCase(editeur)) {
                correspond = false;
            }
            if (type != null && !type.isEmpty() && !document.getClass().getSimpleName().equalsIgnoreCase(type)) {
                correspond = false;
            }
            if (correspond) {
                resultats.add(document);
            }
        }
        afficherResultatsRecherche(resultats);
        return resultats;
    }*/

    public void afficherResultatsRecherche(List<Document> documents) {
        for (Document document : documents) {
            System.out.println("---------------------------------");
            System.out.println("ID: " + document.getId());
            System.out.println("Titre: " + document.getTitre());
            System.out.println("Année de publication: " + document.getAnneePublication());
            System.out.println("Nombre d'exemplaires: " + document.getNombreExemplaires());
            System.out.println("Type: " + document.getClass().getSimpleName());
            if (document instanceof Livre) {
                System.out.println("Auteur: " + ((Livre) document).getAuteur());
                System.out.println("Éditeur: " + ((Livre) document).getEditeur());
                System.out.println("Durée d'emprunt: 3 semaines");
            } else if (document instanceof Cd) {
                System.out.println("Artiste: " + ((Cd) document).getArtiste());
                System.out.println("Durée d'emprunt: 2 semaines");
            } else if (document instanceof Dvd) {
                System.out.println("Réalisateur: " + ((Dvd) document).getRealisateur());
                System.out.println("Durée d'emprunt: 1 semaine");
            }
            System.out.println("Disponible: " + (verifierQuantiteDocuments(document.getId()) > 0 ? "Oui" : "Non"));
            System.out.println();
        }
    }

    public int verifierQuantiteDocuments(long documentIdRecherche) {
        List<Emprunteur> emprunteurs = getTousEmprunteurs();
        int borrowedCount = 0;

        for (Emprunteur emprunteur : emprunteurs) {
            for (Emprunt emprunt : emprunteur.getEmprunts()) {
                for (EmpruntDetail empruntDetail : emprunt.getEmpruntDetails()) {
                    if (empruntDetail.getLineItemID() == documentIdRecherche) {
                        if (empruntDetail.getDateRetourActuelle() == null) {
                            borrowedCount++;
                        }
                    }
                }
            }
        }

        Document document = getDocumentParId(documentIdRecherche);
        if (document == null) {
            throw new RuntimeException("Document non trouvé: " + documentIdRecherche);
        }

        int availableCount = document.getNombreExemplaires() - borrowedCount;
        if (availableCount < 0) {
            throw new RuntimeException("Quantité incorrecte pour le document: " + document.getTitre());
        }

        return availableCount;
    }
}