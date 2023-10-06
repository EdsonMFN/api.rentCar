package api.rentCar.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Type {

    CREDIT_CARD(1,"credit card"),
    DEBIT_CARD(2,"debit card"),
    PIX(3,"pix"),
    TICKET(4,"ticket");

    private final Integer id;
    private final String description;

    Type(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Type findById(Integer id){
        return Stream.of(Type.values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Type %s not found",id)));
    }
    public static Type findByDescription(String description){
        return Stream.of(Type.values())
                .filter(value -> value.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Type %s not found",description)));
    }
}
