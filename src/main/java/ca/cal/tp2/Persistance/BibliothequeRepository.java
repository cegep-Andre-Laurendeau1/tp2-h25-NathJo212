package ca.cal.tp2.Persistance;

import ca.cal.tp2.Modele.Livre;

import java.sql.*;

public class BibliothequeRepository extends RepositoryParentJDBC implements PersistanceGenerique <Livre> {

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
}