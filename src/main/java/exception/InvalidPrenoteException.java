package exception;

public class InvalidPrenoteException extends RuntimeException{
    public InvalidPrenoteException(String ErrorMessage, Throwable error) {
        super(ErrorMessage, error);
    }
}
