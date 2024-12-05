package co.edu.uptc.exceptions;

import java.util.List;

public class ParameterErrorException extends RuntimeException {
    List<String> errorCodes;

    public ParameterErrorException(String message, List<String> errorCodes) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
