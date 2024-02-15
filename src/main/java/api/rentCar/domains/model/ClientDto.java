package api.rentCar.domains.model;

import api.rentCar.domains.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

        private Long id;
        private String name;
        private String cpf;
        private Integer age;
        private String rg;
        private LocalDate dateOfBirth;
        private String phone;
        private String photo;
        private AddressDto address;

        public ClientDto(Client client) {
                this.id = client.getId();
                this.name = client.getName();
                this.cpf = client.getCpf();
                this.age = client.getAge();
                this.rg = client.getRg();
                this.dateOfBirth = client.getDateOfBirth();
                this.phone = client.getPhone();
                this.photo = client.getPhoto();
                this.address = new AddressDto();
        }
}
