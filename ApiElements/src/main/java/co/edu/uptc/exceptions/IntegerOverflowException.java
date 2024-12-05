package co.edu.uptc.exceptions;

import java.util.List;

public class IntegerOverflowException extends RuntimeException {
    List<String> errorCodes;

    public IntegerOverflowException(String message, List<String> errorCodes) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
