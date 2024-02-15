package api.rentCar.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HandlerEntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID= 1L;

    public HandlerEntityNotFoundException(String msg){
        super(msg);
    }
}
