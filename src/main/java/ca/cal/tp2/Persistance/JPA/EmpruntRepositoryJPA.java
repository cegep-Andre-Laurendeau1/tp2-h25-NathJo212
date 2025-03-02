package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Emprunt;
import ca.cal.tp2.Persistance.EmpruntRepository;
import jakarta.persistence.*;

public class EmpruntRepositoryJPA implements EmpruntRepository {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Emprunt emprunt) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(emprunt);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'Emprunt : " + e.getMessage(), e);
        }
    }
}