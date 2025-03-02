package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Persistance.DocumentRepository;
import jakarta.persistence.*;

import java.util.List;

public class CdRepositoryJPA implements DocumentRepository<Cd> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Cd cd) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager();) {
            entityManager.getTransaction().begin();
            entityManager.persist(cd);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du Cd : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cd> rechercheDocuments(String titre, String auteur, Integer annee) {
        String query = "SELECT c FROM Cd c WHERE 1=1";
        if (titre != null && !titre.isEmpty()) {
            query += " AND c.titre LIKE :titre";
        }
        if (auteur != null && !auteur.isEmpty()) {
            query += " AND c.artiste = :artiste";
        }
        if (annee != null) {
            query += " AND c.anneePublication = :annee";
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Cd> typedQuery = entityManager.createQuery(query, Cd.class);
            if (titre != null && !titre.isEmpty()) {
                typedQuery.setParameter("titre", "%" + titre + "%");
            }
            if (auteur != null && !auteur.isEmpty()) {
                typedQuery.setParameter("artiste", auteur);
            }
            if (annee != null) {
                typedQuery.setParameter("annee", annee);
            }
            return typedQuery.getResultList();
        }
    }
}