package ca.cal.tp2;

import ca.cal.tp2.Persistance.*;
import ca.cal.tp2.Service.BibliothequeService;
import ca.cal.tp2.Service.EmprunteurService;
import ca.cal.tp2.Service.PreposeService;
import ca.cal.tp2.Service.dto.DocumentDTO;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Démarrer le serveur TCP
        TcpServer.startTcpServer();

        // Initialiser les services et les repositories
        final EmprunteurRepositoryJPA emprunteurRepositoryJPA = new EmprunteurRepositoryJPA();
        final EmpruntRepositoryJPA empruntRepositoryJPA = new EmpruntRepositoryJPA();
        final BibliothequeService bibliothequeService = new BibliothequeService(new DocumentRepositoryJPA(), emprunteurRepositoryJPA);
        final PreposeService preposeService = new PreposeService(new DocumentRepositoryJPA(), emprunteurRepositoryJPA);
        final EmprunteurService emprunteurService = new EmprunteurService(empruntRepositoryJPA, bibliothequeService, emprunteurRepositoryJPA);

        // Enregistrer des nouveaux clients (emprunteurs)
        preposeService.ajouterEmprunteur("John", "Doe", "john.doe@example.com");
        preposeService.ajouterEmprunteur("Jane", "Smith", "jane.smith@example.com");


        // Ajouter des livres, CD et DVD à la bibliothèque
        preposeService.ajouterLivre("L'Aiguille creuse", 1909, 5, "Maurice Leblanc", "Éditions Pierre Lafitte", 345);
        preposeService.ajouterCd("Weezer", 1994, 10, "Weezer", 12);
        preposeService.ajouterDvd("Inception", 2010, 1, "Christopher Nolan", 148);

        // Recherche de documents par titre, auteur et année
        System.out.println("Recherche de livres par titre:");
        DocumentDTO documentDTO1 = bibliothequeService.rechercherDocument("Aiguille", null, null);
        System.out.println(documentDTO1);

        System.out.println("Recherche de CD par artiste:");
        DocumentDTO documentDTO2 = bibliothequeService.rechercherDocument(null, "Weezer", null);
        System.out.println(documentDTO2);

        System.out.println("Recherche de DVD par titre:");
        DocumentDTO documentDTO3 = bibliothequeService.rechercherDocument("inception", null, null);
        System.out.println(documentDTO3);

        // Emprunter des documents par John Doe
        emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList(documentDTO1.getTitre(), documentDTO2.getTitre(), documentDTO3.getTitre()), 2023, 10, 1);

        // Afficher les emprunts de John Doe
        System.out.println();
        System.out.println("Emprunts de John Doe:");
        System.out.println(bibliothequeService.rechercherEmprunteurParEmail("john.doe@example.com"));

        // Essayer d'emprunter le même document à nouveau (devrait échouer)
        try {
            emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList(documentDTO3.getTitre()), 2023, 10, 2);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Recherche de DVD sans exemplaire disponible
        System.out.println();
        System.out.println("Recherche de DVD sans exemplaire disponible:");
        System.out.println(bibliothequeService.rechercherDocument(documentDTO3.getTitre(), null, null));

        // Emprunter des documents par Jane Smith
        emprunteurService.emprunterDocuments("jane.smith@example.com", Arrays.asList(documentDTO1.getTitre(), documentDTO2.getTitre()), 2023, 10, 3);

        // Afficher les emprunts de Jane Smith
        System.out.println();
        System.out.println("Emprunts de Jane Smith:");
        System.out.println(bibliothequeService.rechercherEmprunteurParEmail("jane.smith@example.com"));
    }
}