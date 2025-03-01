package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Livre;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class LivreRepositoryJPA implements PersistanceGenerique<Livre> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Livre livre) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(livre);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du Livre : " + e.getMessage(), e);
        }
    }

    @Override
    public Livre getById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Livre> query = entityManager.createQuery("SELECT l FROM Livre l WHERE l.id = :id", Livre.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération du Livre par ID : " + e.getMessage(), e);
        }
    }
}