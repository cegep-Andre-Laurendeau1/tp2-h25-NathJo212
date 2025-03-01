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
            if (livre.getId() == 0) {
                entityManager.persist(livre);
            } else {
                entityManager.merge(livre);
            }
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving Livre: " + e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Livre getById(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Livre> query = entityManager.createQuery("SELECT l FROM Livre l WHERE l.id = :id", Livre.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error retrieving Livre by ID: " + e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}