package api.rentCar.domains.model;

import api.rentCar.domains.entity.Address;
import api.rentCar.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;
    private String address;
    private int number;
    private State state;
    private String district;
    private String complement;
    private String cep;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.number = address.getNumber();
        this.state = address.getState();
        this.district = address.getDistrict();
        this.complement = address.getComplement();
        this.cep = address.getCep();
    }
}
