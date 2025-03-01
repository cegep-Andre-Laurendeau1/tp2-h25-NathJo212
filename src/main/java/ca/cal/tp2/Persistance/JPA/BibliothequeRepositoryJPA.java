package ca.cal.tp2.Persistance.JPA;

import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Document;
import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Modele.Livre;
import ca.cal.tp2.Persistance.PersistanceGenerique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BibliothequeRepositoryJPA implements PersistanceGenerique<Document> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("nathan.pu");
    private final LivreRepositoryJPA livreRepository = new LivreRepositoryJPA();
    private final CdRepositoryJPA cdRepository = new CdRepositoryJPA();
    private final DvdRepositoryJPA dvdRepository = new DvdRepositoryJPA();

    @Override
    public void save(Document document) {
        if (document instanceof Livre) {
            livreRepository.save((Livre) document);
        } else if (document instanceof Cd) {
            cdRepository.save((Cd) document);
        } else if (document instanceof Dvd) {
            dvdRepository.save((Dvd) document);
        } else {
            try(EntityManager entityManager = entityManagerFactory.createEntityManager();) {
                entityManager.getTransaction().begin();
                entityManager.persist(document);
                entityManager.getTransaction().commit();
            }catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public Document getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Document document = entityManager.find(Document.class, id);
        if (document == null) {
            document = entityManager.find(Livre.class, id);
        }
        if (document == null) {
            document = entityManager.find(Cd.class, id);
        }
        if (document == null) {
            document = entityManager.find(Dvd.class, id);
        }
        entityManager.close();
        return document;
    }
}