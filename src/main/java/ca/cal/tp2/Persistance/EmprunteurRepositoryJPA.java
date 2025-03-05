package ca.cal.tp2.Persistance;

import ca.cal.tp2.Modele.Emprunt;
import ca.cal.tp2.Modele.Emprunteur;
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
            entityManager.getTransaction().begin();
            var sql = """
                SELECT e FROM Emprunteur e 
                left join fetch e.emprunts em
                left join fetch e.amendes
                left join fetch em.empruntDetails
                WHERE e.email = :email
            """;
            TypedQuery<Emprunteur> query = entityManager.createQuery(sql, Emprunteur.class);
            query.setParameter("email", email);
            Emprunteur emprunteur = query.getSingleResult();
            entityManager.getTransaction().commit();
            return emprunteur;
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'Emprunteur par email: " + e.getMessage(), e);
        }
    }
}