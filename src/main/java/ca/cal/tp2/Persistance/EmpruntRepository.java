package ca.cal.tp2.Persistance;

import ca.cal.tp2.Exceptions.DatabaseErrorExceptionHandler;
import ca.cal.tp2.Modele.Emprunt;

public interface EmpruntRepository {
    void save(Emprunt emprunt) throws DatabaseErrorExceptionHandler;
    int documentEmprunterCount(Long id) throws DatabaseErrorExceptionHandler;
}
