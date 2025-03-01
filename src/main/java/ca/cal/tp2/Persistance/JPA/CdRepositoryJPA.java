package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CdRepositoryJPA implements PersistanceGenerique<Cd> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Cd cd) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cd);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Cd getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cd cd = entityManager.find(Cd.class, id);
        entityManager.close();
        return cd;
    }
}