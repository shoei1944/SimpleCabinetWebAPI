package pro.gravit.simplecabinet.web.exception;

public class AbstractCabinetException extends RuntimeException {
    private final int code;

    public AbstractCabinetException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
