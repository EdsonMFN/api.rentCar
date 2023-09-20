package api.rentCar.exceptions.handlers;

public class HandlerErrorException extends RuntimeException{
    public HandlerErrorException(String msg){
        super(msg);
    }
}
