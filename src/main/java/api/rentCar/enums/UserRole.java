package api.rentCar.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum UserRole {

    ADMIN(1,"admin"),
    USER(2,"user");

    private final Integer id;
    private final String description;

    UserRole(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static UserRole findById(Integer id){
        return Stream.of(UserRole.values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("File %s not found", id)));
    }
    public static UserRole findByDescription(String description){
        return Stream.of(UserRole.values())
                .filter(value -> value.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("File %s not found", description)));
    }
}
