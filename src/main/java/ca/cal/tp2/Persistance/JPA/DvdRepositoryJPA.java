package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DvdRepositoryJPA implements PersistanceGenerique<Dvd> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Dvd dvd) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(dvd);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Dvd getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Dvd dvd = entityManager.find(Dvd.class, id);
        entityManager.close();
        return dvd;
    }
}