package ca.cal.tp2;

import ca.cal.tp2.Persistance.JDBC.BibliothequeRepositoryJDBC;
import ca.cal.tp2.Persistance.JPA.BibliothequeRepositoryJPA;
import ca.cal.tp2.Service.BibliothequeService;
import ca.cal.tp2.Service.PreposeService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Démarrer le serveur TCP
        TcpServer.startTcpServer();

        // Initialiser les services et le repository
        final BibliothequeRepositoryJPA bibliotequeRepositoryJPA = new BibliothequeRepositoryJPA();
        final BibliothequeService bibliothequeService = new BibliothequeService(bibliotequeRepositoryJPA);
        final PreposeService preposeService = new PreposeService(bibliothequeService, bibliotequeRepositoryJPA);

        // Ajouter un livre à la bibliothèque
        preposeService.ajouterLivre("L'Aiguille creuse", 1909, 5, "Maurice Leblanc", "Éditions Pierre Lafitte", 345);

        // Rechercher un document par titre
        System.out.println(bibliothequeService.getDocumentParId(1));
    }
}