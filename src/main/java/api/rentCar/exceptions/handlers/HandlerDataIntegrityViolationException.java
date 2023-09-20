package api.rentCar.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class HandlerDataIntegrityViolationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID= 1L;

    public HandlerDataIntegrityViolationException(String msg){
        super(msg);
    }
}
