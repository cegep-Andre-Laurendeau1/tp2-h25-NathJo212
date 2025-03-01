package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;
import ca.cal.tp2.Persistance.JDBC.BibliothequeRepositoryJDBC;
import ca.cal.tp2.Persistance.PersistanceGenerique;

import java.util.List;

public class PreposeService {
    private final PersistanceGenerique persistanceGenerique;
    private final BibliothequeService bibliothequeService;

    public PreposeService(BibliothequeService bibliothequeService, PersistanceGenerique persistanceGenerique) {
        this.bibliothequeService = bibliothequeService;
        this.persistanceGenerique = persistanceGenerique;
    }

    public void ajouterLivre(String titre, int anneePublication, int nbExemplaires, String auteur, String editeur, int nbPages) {
        persistanceGenerique.save(new Livre(0, titre, anneePublication, nbExemplaires, auteur, editeur, nbPages));
    }

    public void ajouterCd(String titre, int anneePublication, int nbExemplaires, String artiste, int nbMinutes) {
        persistanceGenerique.save(new Cd(0, titre, anneePublication, nbExemplaires, artiste, nbMinutes));
    }

    public void ajouterDvd(String titre, int anneePublication, int nbExemplaires, String realisateur, int nbMinutes) {
        persistanceGenerique.save(new Dvd(0, titre, anneePublication, nbExemplaires, realisateur, nbMinutes));
    }

    public void ajouterEmprunteur(long id, String nom, String prenom) {
        bibliothequeService.ajouterEmprunteur(new Emprunteur(id, nom, prenom));
    }

    public void rapportAmendes() {
        System.out.println("---------------------------------");
        List<Emprunteur> emprunteurs = bibliothequeService.getTousEmprunteurs();
        System.out.println("Rapport des Amendes:");
        for (Emprunteur emprunteur : emprunteurs) {
            for (Amendes amende : emprunteur.getAmendes()) {
                System.out.println("Emprunteur ID: " + emprunteur.getId() + ", Nom: " + emprunteur.getNom() + ", Prénom: " + emprunteur.getPrenom());
                System.out.println(" - Amende ID: " + amende.getId() + ", Montant: " + amende.getMontant() + ", Date émise: " + amende.getDateEmis() + ", Payée: " + amende.isPaye());
                if (amende.isPaye()) {
                    System.out.println("   Date de paiement: " + amende.getDatePaye());
                }
            }
        }
        System.out.println();
    }

    public void rapportEmprunts() {
        System.out.println("---------------------------------");
        List<Emprunteur> emprunteurs = bibliothequeService.getTousEmprunteurs();
        System.out.println("Rapport des Emprunts:");
        for (Emprunteur emprunteur : emprunteurs) {
            for (Emprunt emprunt : emprunteur.getEmprunts()) {
                System.out.println("Emprunteur ID: " + emprunteur.getId() + ", Nom: " + emprunteur.getNom() + ", Prénom: " + emprunteur.getPrenom());
                System.out.println(" - Emprunt ID: " + emprunt.getId() + ", Date d'emprunt: " + emprunt.getEmprunt());
                for (EmpruntDetail empruntDetail : emprunt.getEmpruntDetails()) {
                    Document document = bibliothequeService.getDocumentParId(empruntDetail.getLineItemID());
                    System.out.println("   - Document ID: " + document.getId() + ", Titre: " + document.getTitre() + ", Date de retour prévue: " + empruntDetail.getDateRetourPrevue() + ", Statut: " + empruntDetail.getStatus());
                    if (empruntDetail.getDateRetourActuelle() != null) {
                        System.out.println("     Date de retour actuelle: " + empruntDetail.getDateRetourActuelle());
                    }
                }
            }
        }
        System.out.println();
    }
}