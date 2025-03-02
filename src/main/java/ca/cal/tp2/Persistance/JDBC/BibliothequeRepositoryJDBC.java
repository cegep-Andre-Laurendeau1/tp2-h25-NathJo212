package ca.cal.tp2.Persistance.JDBC;

import ca.cal.tp2.Modele.Livre;
import ca.cal.tp2.Persistance.DocumentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliothequeRepositoryJDBC extends RepositoryParentJDBC implements DocumentRepository<Livre> {

    public void save(Livre livre) {
        String requete = """
                insert into Livre values (?, ?, ?, ?, ?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(requete)) {
            preparedStatement.setLong(1, livre.getId());
            preparedStatement.setString(2, livre.getTitre());
            preparedStatement.setInt(3, livre.getAnneePublication());
            preparedStatement.setInt(4, livre.getNombreExemplaires());
            preparedStatement.setString(5, livre.getAuteur());
            preparedStatement.setString(6, livre.getEditeur());
            preparedStatement.setInt(7, livre.getNbPages());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimerLivre(long id) {
        String requete = """
                delete from Livre where id = ?;
                """;
        try (PreparedStatement preparedStatement = conn.prepareStatement(requete)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Livre getById(Long id) {
        String requete = "SELECT * FROM Livre WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(requete)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Livre(
                        resultSet.getLong("id"),
                        resultSet.getString("titre"),
                        resultSet.getInt("anneePublication"),
                        resultSet.getInt("nbExemplaires"),
                        resultSet.getString("auteur"),
                        resultSet.getString("editeur"),
                        resultSet.getInt("nbPages")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du livre avec l'ID " + id, e);
        }
        return null;
    }

    public List<Livre> rechercheDocuments(String titre, String auteur, Integer annee) {
        List<Livre> resultats = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Livre WHERE 1=1");
        if (titre != null && !titre.isEmpty()) {
            query.append(" AND titre LIKE ?");
        }
        if (auteur != null && !auteur.isEmpty()) {
            query.append(" AND auteur = ?");
        }
        if (annee != null) {
            query.append(" AND anneePublication = ?");
        }

        try (PreparedStatement preparedStatement = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (titre != null && !titre.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + titre + "%");
            }
            if (auteur != null && !auteur.isEmpty()) {
                preparedStatement.setString(paramIndex++, auteur);
            }
            if (annee != null) {
                preparedStatement.setInt(paramIndex, annee);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultats.add(new Livre(
                        resultSet.getLong("id"),
                        resultSet.getString("titre"),
                        resultSet.getInt("anneePublication"),
                        resultSet.getInt("nbExemplaires"),
                        resultSet.getString("auteur"),
                        resultSet.getString("editeur"),
                        resultSet.getInt("nbPages")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des documents", e);
        }
        return resultats;
    }
}