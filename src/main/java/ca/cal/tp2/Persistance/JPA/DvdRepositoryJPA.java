package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Modele.Livre;
import ca.cal.tp2.Persistance.DocumentRepository;
import jakarta.persistence.*;

import java.util.List;

public class DvdRepositoryJPA implements DocumentRepository<Dvd> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Dvd dvd) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(dvd);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du Livre : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Dvd> rechercheDocuments(String titre, String auteur, Integer annee) {
        String query = "SELECT d FROM Dvd d WHERE 1=1";
        if (titre != null && !titre.isEmpty()) {
            query += " AND d.titre LIKE :titre";
        }
        if (auteur != null && !auteur.isEmpty()) {
            query += " AND d.auteur = :auteur";
        }
        if (annee != null) {
            query += " AND d.anneePublication = :annee";
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Dvd> typedQuery = entityManager.createQuery(query, Dvd.class);
            if (titre != null && !titre.isEmpty()) {
                typedQuery.setParameter("titre", "%" + titre + "%");
            }
            if (auteur != null && !auteur.isEmpty()) {
                typedQuery.setParameter("auteur", auteur);
            }
            if (annee != null) {
                typedQuery.setParameter("annee", annee);
            }
            return typedQuery.getResultList();
        }
    }
}