package ca.cal.tp2.Persistance;

public interface PersistanceGenerique<T> {
    void save(T objet);
    T getById(Long id);
}
