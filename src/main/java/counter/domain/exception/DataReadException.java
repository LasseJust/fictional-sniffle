package counter.domain.exception;

public class DataReadException extends RuntimeException {
    public DataReadException(Throwable cause) {
        super(cause);
    }
}
