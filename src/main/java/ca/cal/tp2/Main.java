package ca.cal.tp2;

import ca.cal.tp2.Persistance.JPA.*;
import ca.cal.tp2.Service.BibliothequeService;
import ca.cal.tp2.Service.EmprunteurService;
import ca.cal.tp2.Service.PreposeService;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Démarrer le serveur TCP
        TcpServer.startTcpServer();

        // Initialiser les services et le repository
        final LivreRepositoryJPA livreRepositoryJPA = new LivreRepositoryJPA();
        final CdRepositoryJPA cdRepositoryJPA = new CdRepositoryJPA();
        final DvdRepositoryJPA dvdRepositoryJPA = new DvdRepositoryJPA();
        final EmprunteurRepositoryJPA emprunteurRepositoryJPA = new EmprunteurRepositoryJPA();
        final EmpruntRepositoryJPA empruntRepositoryJPA = new EmpruntRepositoryJPA();
        final BibliothequeService bibliothequeService = new BibliothequeService(livreRepositoryJPA, cdRepositoryJPA, dvdRepositoryJPA, emprunteurRepositoryJPA);
        final PreposeService preposeService = new PreposeService(livreRepositoryJPA, cdRepositoryJPA, dvdRepositoryJPA, emprunteurRepositoryJPA, bibliothequeService);
        final EmprunteurService emprunteurService = new EmprunteurService(empruntRepositoryJPA, bibliothequeService);

        // Enregistrement d'un nouveau client (emprunteur)
        preposeService.ajouterEmprunteur("John", "Doe", "john.doe@example.com");

        // Ajout de livres, CD et DVD à la bibliothèque
        preposeService.ajouterLivre("L'Aiguille creuse", 1909, 5, "Maurice Leblanc", "Éditions Pierre Lafitte", 345);
        preposeService.ajouterCd("Weezer", 1994, 10, "Weezer", 12);
        preposeService.ajouterDvd("Inception", 2010, 1, "Christopher Nolan", 148);

        // Recherche de livre par titre, auteur et année
        System.out.println("Recherche de livres par titre:");
        System.out.println(bibliothequeService.rechercherDocuments("Aiguille", null, null));
        System.out.println("Recherche de livres par auteur:");
        System.out.println(bibliothequeService.rechercherDocuments(null, "Maurice Leblanc", null));
        System.out.println("Recherche de livres par année:");
        System.out.println(bibliothequeService.rechercherDocuments(null, null, 1909));

        // Emprunt de documents
        emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList("L'Aiguille creuse", "Weezer", "Inception"), 2023, 10, 1);

        // Afficher les emprunts du client
        System.out.println("Emprunts de John Doe:");
        System.out.println(bibliothequeService.rechercherEmprunteurParEmail("john.doe@example.com"));

        // Essayer d'emprunter le même document à nouveau (devrait échouer)
        try {
            emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList("Inception"), 2023, 10, 2);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}