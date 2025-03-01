package ca.cal.tp2.Persistance;

import java.util.List;

public interface PersistanceGenerique<T> {
    void save(T objet);
    T getById(Long id);
}
