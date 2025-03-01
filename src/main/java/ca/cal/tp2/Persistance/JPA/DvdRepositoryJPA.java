package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class DvdRepositoryJPA implements PersistanceGenerique<Dvd> {
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
    public Dvd getById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Dvd> query = entityManager.createQuery("SELECT d FROM Dvd d WHERE d.id = :id", Dvd.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération du Livre par ID : " + e.getMessage(), e);
        }
    }
}