package api.rentCar.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum State {

    AM(1,"Amazonas"),
    AC(2,"Acre"),
    AL(3,"Alagoas"),
    AP(4,"Amapá"),
    BA(5,"Bahia"),
    CE(6,"Ceará"),
    ES(7,"Espírito Santo"),
    GO(8,"Goiás"),
    MA(9,"Maranhão"),
    MT(10,"Mato Grosso"),
    MS(11,"Mato Grosso do Sul"),
    MG(12,"Minas Gerais"),
    PA(13,"Pará"),
    PB(14,"Paraíba"),
    PR(15,"Paraná"),
    PE(16,"Pernambuco"),
    PI(17,"Piauí"),
    RJ(18,"Rio de Janeiro"),
    RN(19,"Rio Grande do Norte"),
    RS(20,"Rio Grande do Sul"),
    RO(21,"Rondônia"),
    RR(22,"Roraima"),
    SC(23,"Santa Catarina"),
    SP(24,"São Paulo"),
    SE(25,"Sergipe"),
    TO(26,"Tocantins"),
    DF(27,"Distrito Federal");

    private final Integer id;
    private final String description;

    State(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static State findById(Integer id){
        return Stream.of(State.values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("File %s not found", id)));
    }
    public static State findByDescription(String description){
        return Stream.of(State.values())
                .filter(value -> value.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("File %s not found", description)));
    }
}
