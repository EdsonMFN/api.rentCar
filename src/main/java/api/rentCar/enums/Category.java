package api.rentCar.enums;

import java.util.IllformedLocaleException;
import java.util.stream.Stream;

public enum Category {

    COMPACT_HATCH(1, "Compact Hatch"),
    MEDIUM_HATCH(2, "Medium Hatch"),
    VAN(3, "Van"),
    PICKUP(4, "Pickup"),
    SEDAN(5, "Sedan");

    private Integer id;
    private String desciption;

     Category(Integer id, String desciption) {
        this.id = id;
        this.desciption = desciption;
    }

    public Integer getId() {
        return id;
    }

    public String getDesciption() {
        return desciption;
    }
    public static Category findById(Integer id){
        return Stream.of(Category.values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllformedLocaleException(String.format("Categoria %s não localizada",id)));
    }
    public static Category findByDescription(String desciption){
        return Stream.of(Category.values())
                .filter(value -> value.getDesciption().equals(desciption))
                .findFirst()
                .orElseThrow(() -> new IllformedLocaleException(String.format("Categoria %s não localizada",desciption)));
    }
}
