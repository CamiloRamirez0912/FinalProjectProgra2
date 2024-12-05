package co.edu.uptc.exceptions;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private List<String> errorCodes;
    
    public ErrorResponse(int status, String message, List<String> errorCodes) {
        this.status = status;
        this.message = message;
        this.errorCodes = errorCodes;
    }

    
}
