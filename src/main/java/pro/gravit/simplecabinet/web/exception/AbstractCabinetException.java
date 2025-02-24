package pro.gravit.simplecabinet.web.exception;

import lombok.Getter;

@Getter
public class AbstractCabinetException extends RuntimeException {
    private final int code;

    public AbstractCabinetException(String message, int code) {
        super(message);
        this.code = code;
    }

}
