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

        // Ajouter un livre à la bibliothèque
        preposeService.ajouterLivre("L'Aiguille creuse", 1909, 5, "Maurice Leblanc", "Éditions Pierre Lafitte", 345);

        // Rechercher un document par titre
        System.out.println(bibliothequeService.rechercherDocuments("Aiguille", null, null));

        // Ajouter un CD à la bibliothèque
        preposeService.ajouterCd("Weezer", 1994, 10, "Weezer", 12);

        // Rechercher un CD par titre
        System.out.println(bibliothequeService.rechercherDocuments("Weezer", null, null));

        // Ajouter un DVD à la bibliothèque
        preposeService.ajouterDvd("Inception", 2010, 1, "Christopher Nolan", 148);

        // Rechercher un DVD par titre
        System.out.println(bibliothequeService.rechercherDocuments("Inception", null, null));

        //Ajout d'un document avec la même année de publication
        preposeService.ajouterDvd("Shutter Island", 2010, 2, "Martin Scorsese", 138);

        // Rechercher un DVD par titre
        System.out.println(bibliothequeService.rechercherDocuments(null, null, 2010));

        // Ajouter un emprunteur
        preposeService.ajouterEmprunteur("John", "Doe", "john.doe@example.com");

        System.out.println(bibliothequeService.rechercherEmprunteurParEmail("john.doe@example.com"));

        // Emprunter des documents
        emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList("L'Aiguille creuse", "Weezer", "Inception"), 2023, 10, 1);

        System.out.println(bibliothequeService.rechercherEmprunteurParEmail("john.doe@example.com"));

        // Essayer d'emprunter le même document à nouveau (devrait échouer)
        try {
            emprunteurService.emprunterDocuments("john.doe@example.com", Arrays.asList("Inception"), 2023, 10, 2);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}