package ca.cal.tp2.Persistance;

import ca.cal.tp2.Exceptions.DatabaseErrorExceptionHandler;
import ca.cal.tp2.Modele.Emprunt;
import jakarta.persistence.*;

public class EmpruntRepositoryJPA implements EmpruntRepository {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Emprunt emprunt) throws DatabaseErrorExceptionHandler {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(emprunt);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new DatabaseErrorExceptionHandler(e.getMessage());
        }
    }

    @Override
    public int documentEmprunterCount(Long id) throws DatabaseErrorExceptionHandler {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            String query = "SELECT COUNT(ed) FROM EmpruntDetail ed WHERE ed.document.id = :documentId AND ed.dateRetourActuelle IS NULL";
            TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
            typedQuery.setParameter("documentId", id);
            entityManager.getTransaction().commit();
            return typedQuery.getSingleResult().intValue();
        } catch (RuntimeException e) {
            throw new DatabaseErrorExceptionHandler(e.getMessage());
        }

    }
}