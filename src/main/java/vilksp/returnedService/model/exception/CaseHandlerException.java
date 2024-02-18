package vilksp.returnedService.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CaseHandlerException extends RuntimeException {
    public CaseHandlerException(String message) {
        super(message);
    }
}
