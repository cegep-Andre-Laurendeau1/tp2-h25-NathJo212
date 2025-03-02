package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Emprunteur;
import ca.cal.tp2.Persistance.EmprunteurRepository;
import jakarta.persistence.*;

import java.util.List;

public class EmprunteurRepositoryJPA implements EmprunteurRepository {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");

    @Override
    public void save(Emprunteur emprunteur) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(emprunteur);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Emprunteur getByEmail(String email) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Emprunteur> query = entityManager.createQuery(
                    "SELECT e FROM Emprunteur e WHERE e.email = :email", Emprunteur.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'Emprunteur par prénom et nom : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Emprunteur> getAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Emprunteur> query = entityManager.createQuery("SELECT e FROM Emprunteur e", Emprunteur.class);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les Emprunteurs : " + e.getMessage(), e);
        }
    }
}