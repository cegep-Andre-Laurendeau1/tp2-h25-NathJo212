package ca.cal.tp2;

import ca.cal.tp2.Persistence.BibliothequeRepository;
import ca.cal.tp2.Service.BibliothequeService;
import ca.cal.tp2.Service.EmprunteurService;
import ca.cal.tp2.Service.PreposeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Démarrer le serveur TCP
        TcpServer.startTcpServer();

        // Initialiser les services et le repository
        final BibliothequeRepository bibliothequeRepository = new BibliothequeRepository();
        final BibliothequeService bibliothequeService = new BibliothequeService(bibliothequeRepository);
        final PreposeService preposeService = new PreposeService(bibliothequeService, bibliothequeRepository);
        final EmprunteurService emprunteurService = new EmprunteurService(bibliothequeService);

        // Ajouter des documents à la bibliothèque
        preposeService.ajouterLivre(1, "L'Aiguille creuse", 1909, 5, "Maurice Leblanc", "Éditions Pierre Lafitte", 345);
        preposeService.ajouterCd(2, "Weezer", 1994, 25, "Weezer", 41);
        preposeService.ajouterDvd(3, "Inception", 2010, 1, "Christopher Nolan", 148);
        preposeService.ajouterLivre(4, "Le Grand Meaulnes", 1913, 3, "Alain-Fournier", "Éditions Émile-Paul Frères", 254);
        preposeService.ajouterCd(5, "The Dark Side of the Moon", 1973, 10, "Pink Floyd", 43);
        preposeService.ajouterDvd(6, "The Dark Knight", 2008, 4, "Christopher Nolan", 152);

        // Afficher et retirer des documents
        System.out.println(bibliothequeService.getDocumentParId(4L));
        preposeService.retirerLivre(4);
        System.out.println(bibliothequeService.getDocumentParId(4L));

        System.out.println(bibliothequeService.getDocumentParId(5L));
        preposeService.retirerCd(5);
        System.out.println(bibliothequeService.getDocumentParId(5L));

        System.out.println(bibliothequeService.getDocumentParId(6L));
        preposeService.retirerDvd(6);
        System.out.println(bibliothequeService.getDocumentParId(6L));

        // Ajouter un emprunteur
        preposeService.ajouterEmprunteur(1, "Marcel", "Proust");

        // Emprunter des documents
        List<Long> documentsEmprunter = new ArrayList<>();
        documentsEmprunter.add(1L);
        documentsEmprunter.add(2L);
        documentsEmprunter.add(3L);

        emprunteurService.emprunterDocuments(1, documentsEmprunter, 2023, 10, 15);
        emprunteurService.afficherEmprunts(1);

        // Retourner des documents
        emprunteurService.retournerDocument(1, 1L, 2023, 10, 20);
        emprunteurService.afficherEmprunts(1);

        emprunteurService.retournerDocument(1, 3L, 2023, 10, 13);
        emprunteurService.afficherEmprunts(1);

        emprunteurService.retournerDocument(1, 2L, 2023, 11, 1);
        emprunteurService.afficherEmprunts(1);

        // Afficher les informations de l'emprunteur
        emprunteurService.afficherInfoEmprunteur(1);

        // Payer des amendes
        emprunteurService.payerAmendes(0.75, 1, 2024, 1, 4);
        emprunteurService.afficherInfoEmprunteur(1);

        // Emprunter à nouveau après paiement des amendes
        documentsEmprunter = new ArrayList<>();
        documentsEmprunter.add(1L);
        emprunteurService.emprunterDocuments(1, documentsEmprunter, 2024, 1, 5);
        emprunteurService.afficherInfoEmprunteur(1);

        // Afficher les rapports des amendes et des emprunts
        preposeService.rapportAmendes();
        preposeService.rapportEmprunts();

        bibliothequeService.rechercherDocuments("Inception", null, null, "Dvd");

        // Tests supplémentaires
        // Ajouter un nouveau livre et vérifier son ajout
        preposeService.ajouterLivre(7, "1984", 1949, 10, "George Orwell", "Secker & Warburg", 328);
        System.out.println(bibliothequeService.getDocumentParId(7L));

        // Emprunter le nouveau livre
        documentsEmprunter = new ArrayList<>();
        documentsEmprunter.add(7L);
        emprunteurService.emprunterDocuments(1, documentsEmprunter, 2024, 1, 10);
        emprunteurService.afficherEmprunts(1);

        // Retourner le nouveau livre
        emprunteurService.retournerDocument(1, 7L, 2024, 1, 20);
        emprunteurService.afficherEmprunts(1);

        // Ajouter un autre emprunteur
        preposeService.ajouterEmprunteur(2, "Albert", "Camus");

        // Tenter d'emprunter un document déjà emprunté
        documentsEmprunter = new ArrayList<>();
        documentsEmprunter.add(2L);
        documentsEmprunter.add(3L);
        emprunteurService.emprunterDocuments(1, documentsEmprunter, 2024, 2, 10);
        emprunteurService.afficherEmprunts(1);

        List<Long> documentsEmprunter2 = new ArrayList<>();
        documentsEmprunter2.add(2L);
        documentsEmprunter2.add(3L); // Document déjà emprunté par Marcel Proust
        try {
            emprunteurService.emprunterDocuments(2, documentsEmprunter2, 2024, 2, 16);
        } catch (RuntimeException e) {
            System.out.println("Erreur: " + e.getMessage());
            System.out.println();
        }

        // Tester d'emprunter un document si on a des amendes
        emprunteurService.retournerDocument(1, 2L, 2024, 2, 15);
        emprunteurService.retournerDocument(1, 3L, 2024, 3, 15);

        emprunteurService.afficherInfoEmprunteur(1);

        List<Long> nouvelEmprunt = new ArrayList<>();
        nouvelEmprunt.add(2L);

        try {
            emprunteurService.emprunterDocuments(1, nouvelEmprunt, 2024, 5, 1);
        } catch (RuntimeException e) {
            System.out.println("Erreur attendue: " + e.getMessage());
        }

        emprunteurService.payerAmendes(6.5, 1, 2024, 5, 2);

        emprunteurService.emprunterDocuments(1, nouvelEmprunt, 2024, 5, 3);

        emprunteurService.afficherInfoEmprunteur(1);

        // Afficher les informations des emprunteurs
        emprunteurService.afficherInfoEmprunteur(1);
        emprunteurService.afficherInfoEmprunteur(2);

        preposeService.rapportAmendes();
        preposeService.rapportEmprunts();
    }
}