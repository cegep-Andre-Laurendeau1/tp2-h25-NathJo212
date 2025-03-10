package ca.cal.tp2.Exceptions;

public class DatabaseErrorExceptionHandler extends Throwable {
    public DatabaseErrorExceptionHandler(String message) {
        super(message);
    }
}