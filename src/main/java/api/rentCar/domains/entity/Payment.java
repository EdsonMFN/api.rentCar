package api.rentCar.domains.entity;

import api.rentCar.domains.model.PaymentDto;
import api.rentCar.enums.Status;
import api.rentCar.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    @PrimaryKeyJoinColumns(value = @PrimaryKeyJoinColumn )
    private Long id;

    @OneToOne
    @JoinColumn(name = "rent",nullable = false)
    private Rent rent;

    @Column(name = "payday",nullable = false)
    private LocalDate payday;

    @Enumerated(EnumType.STRING)
    @Column(name = "typePayment",nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusPayment",nullable = false)
    private Status status;

    public Payment(PaymentDto paymentDto) {
        this.rent = new Rent();
        this.payday = paymentDto.getPayday();
        this.type = paymentDto.getType();
        this.status = paymentDto.getStatus();
    }
}
