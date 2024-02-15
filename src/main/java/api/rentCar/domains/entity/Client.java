package api.rentCar.domains.entity;

import api.rentCar.domains.model.ClientDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_client")
        private Long id;
        @Column(name = "name",nullable = false)
        private String name;
        @Column(name = "cpf",nullable = false,unique = true)
        @CPF
        private String cpf;
        @Column(name = "age",nullable = false)
        private Integer age;
        @Column(name = "rg",nullable = false)
        private String rg;
        @Column(name = "date_of_birth",nullable = false)
        private LocalDate dateOfBirth;
        @Column(name = "phone",nullable = false,unique = true)
        private String phone;
        @Column(name = "photo", nullable = false)
        private String photo;
        @OneToOne
        @JoinColumn(name = "id_address")
        private Address address;

        public Client(ClientDto clientDto) {
                this.id = clientDto.getId();
                this.name = clientDto.getName();
                this.cpf = clientDto.getCpf();
                this.age = clientDto.getAge();
                this.rg = clientDto.getRg();
                this.dateOfBirth = clientDto.getDateOfBirth();
                this.phone = clientDto.getPhone();
                this.photo = clientDto.getPhoto();
                this.address = new Address();
        }
}
