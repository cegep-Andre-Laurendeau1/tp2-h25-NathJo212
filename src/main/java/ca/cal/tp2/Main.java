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

        // Initialiser les services et le repository
        final EmprunteurRepositoryJPA emprunteurRepositoryJPA = new EmprunteurRepositoryJPA();
        final EmpruntRepositoryJPA empruntRepositoryJPA = new EmpruntRepositoryJPA();
        final BibliothequeService bibliothequeService = new BibliothequeService(new DocumentRepositoryJPA(), emprunteurRepositoryJPA);
        final PreposeService preposeService = new PreposeService(new DocumentRepositoryJPA(), emprunteurRepositoryJPA);
        final EmprunteurService emprunteurService = new EmprunteurService(empruntRepositoryJPA, bibliothequeService, emprunteurRepositoryJPA);

        // Enregistrement d'un nouveau client (emprunteur)
        preposeService.ajouterEmprunteur("John", "Doe", "john.doe@example.com");

        // Ajout de livres, CD et DVD à la bibliothèque
        preposeService.ajouterLivre("L'Aiguille creuse", 1909, 5, "Maurice Leblanc", "Éditions Pierre Lafitte", 345);
        preposeService.ajouterCd("Weezer", 1994, 10, "Weezer", 12);
        preposeService.ajouterDvd("Inception", 2010, 1, "Christopher Nolan", 148);

        // Recherche de livre par titre, auteur et année et aussi avec das majuscules ou sans majuscules
        System.out.println("Recherche de livres par titre:");
        DocumentDTO documentDTO1 = bibliothequeService.rechercherDocument("Aiguille", null, null);
        System.out.println(documentDTO1);
        System.out.println("Recherche de livres par auteur:");
        DocumentDTO documentDTO2 = bibliothequeService.rechercherDocument(null, "Weezer", null);
        System.out.println(documentDTO2);
        System.out.println("Recherche de livres par année:");
        DocumentDTO documentDTO3 = bibliothequeService.rechercherDocument("inception", null, null);
        System.out.println(documentDTO3);

        // Emprunt de documents
        emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList(documentDTO1.getTitre(), documentDTO2.getTitre(), documentDTO3.getTitre()), 2023, 10, 1);

        // Afficher les emprunts du client
        System.out.println("Emprunts de John Doe:");
        System.out.println(bibliothequeService.rechercherEmprunteurParEmail("john.doe@example.com"));

        // Essayer d'emprunter le même document à nouveau (devrait échouer)
        try {
            emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList(documentDTO3.getTitre()), 2023, 10, 2);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Recherche de DVD qui n'as pas d'exemplaire:");
        System.out.println(bibliothequeService.rechercherDocument(documentDTO3.getTitre(), null, null));
    }
}