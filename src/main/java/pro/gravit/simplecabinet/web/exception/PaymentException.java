package pro.gravit.simplecabinet.web.exception;

public class PaymentException extends AbstractCabinetException {

    public PaymentException(String message, int code) {
        super(message, 1000 + code);
    }
}
