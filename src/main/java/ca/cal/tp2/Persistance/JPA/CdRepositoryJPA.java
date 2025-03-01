package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Livre;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class CdRepositoryJPA implements PersistanceGenerique<Cd> {
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
    public Cd getById(Long id) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager();) {
            TypedQuery<Cd> query = entityManager.createQuery("SELECT c FROM Cd l WHERE c.id = :id", Cd.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération du Cd par ID : " + e.getMessage(), e);
        }
    }
}