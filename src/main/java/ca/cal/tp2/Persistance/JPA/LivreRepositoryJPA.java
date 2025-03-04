package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Livre;
import ca.cal.tp2.Persistance.DocumentRepository;
import jakarta.persistence.*;

import java.util.List;

public class LivreRepositoryJPA implements DocumentRepository<Livre> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Livre livre) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.persist(livre);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du Livre : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livre> rechercheDocuments(String titre, String auteur, Integer annee) {
        String query = "SELECT l FROM Livre l WHERE 1=1";
        if (titre != null && !titre.isEmpty()) {
            query += " AND l.titre LIKE :titre";
        }
        if (auteur != null && !auteur.isEmpty()) {
            query += " AND l.auteur = :auteur";
        }
        if (annee != null) {
            query += " AND l.anneePublication = :annee";
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Livre> typedQuery = entityManager.createQuery(query, Livre.class);
            if (titre != null && !titre.isEmpty()) {
                typedQuery.setParameter("titre", "%" + titre.toLowerCase() + "%");
            }
            if (auteur != null && !auteur.isEmpty()) {
                typedQuery.setParameter("auteur", auteur.toLowerCase());
            }
            if (annee != null) {
                typedQuery.setParameter("annee", annee);
            }
            return typedQuery.getResultList();
        }
    }
}