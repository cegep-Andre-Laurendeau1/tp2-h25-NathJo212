package ca.cal.tp2.Persistance;

import ca.cal.tp2.Exceptions.DatabaseErrorExceptionHandler;
import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Document;
import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Modele.Livre;
import jakarta.persistence.*;

import java.util.zip.DataFormatException;

public class DocumentRepositoryJPA implements DocumentRepository {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Document document) throws DatabaseErrorExceptionHandler {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager();) {
            entityManager.getTransaction().begin();
            entityManager.persist(document);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new DatabaseErrorExceptionHandler(e.getMessage());
        }
    }

    @Override
    public Livre rechercheLivre(String titre, String auteur, Integer annee) throws DatabaseErrorExceptionHandler {
        String sql = "SELECT l FROM Livre l WHERE 1=1";
        if (titre != null && !titre.isEmpty()) {
            sql += " AND l.titre LIKE :titre";
        }
        if (auteur != null && !auteur.isEmpty()) {
            sql += " AND l.auteur = :auteur";
        }
        if (annee != null) {
            sql += " AND l.anneePublication = :annee";
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Livre> typedQuery = entityManager.createQuery(sql, Livre.class);
            if (titre != null && !titre.isEmpty()) {
                typedQuery.setParameter("titre", "%" + titre.toLowerCase() + "%");
            }
            if (auteur != null && !auteur.isEmpty()) {
                typedQuery.setParameter("auteur", auteur.toLowerCase());
            }
            if (annee != null) {
                typedQuery.setParameter("annee", annee);
            }
            entityManager.getTransaction().commit();
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw new DatabaseErrorExceptionHandler(e.getMessage());
        }
    }

    @Override
    public Cd rechercheCd(String titre, String artiste) throws DatabaseErrorExceptionHandler {
        String query = "SELECT c FROM Cd c WHERE 1=1";
        if (titre != null && !titre.isEmpty()) {
            query += " AND c.titre LIKE :titre";
        }
        if (artiste != null && !artiste.isEmpty()) {
            query += " AND c.artiste = :artiste";
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Cd> typedQuery = entityManager.createQuery(query, Cd.class);
            if (titre != null && !titre.isEmpty()) {
                typedQuery.setParameter("titre", "%" + titre.toLowerCase() + "%");
            }
            if (artiste != null && !artiste.isEmpty()) {
                typedQuery.setParameter("artiste", artiste.toLowerCase());
            }
            entityManager.getTransaction().commit();
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw new DatabaseErrorExceptionHandler(e.getMessage());
        }
    }

    @Override
    public Dvd rechercheDvd(String titre, String realisateur) throws DatabaseErrorExceptionHandler {
        String query = "SELECT d FROM Dvd d WHERE 1=1";
        if (titre != null && !titre.isEmpty()) {
            query += " AND d.titre LIKE :titre";
        }
        if (realisateur != null && !realisateur.isEmpty()) {
            query += " AND d.realisateur = :realisateur";
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Dvd> typedQuery = entityManager.createQuery(query, Dvd.class);
            if (titre != null && !titre.isEmpty()) {
                typedQuery.setParameter("titre", "%" + titre.toLowerCase() + "%");
            }
            if (realisateur != null && !realisateur.isEmpty()) {
                typedQuery.setParameter("artiste", realisateur.toLowerCase());
            }
            entityManager.getTransaction().commit();
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw new DatabaseErrorExceptionHandler(e.getMessage());
        }
    }
}