package exception;

public class ProcessInvoiceException extends BaseException{
    private static final long serialVersionUID = 1091337136123906298L;

    public ProcessInvoiceException() {

    }

    public ProcessInvoiceException(String message) {
        super(message);
    }
}
