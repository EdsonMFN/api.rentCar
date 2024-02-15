package api.rentCar.domains.entity;

import api.rentCar.domains.model.RentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "rent")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rent")
    @PrimaryKeyJoinColumns(value = {@PrimaryKeyJoinColumn})
    private Long id;
    @OneToOne
    @JoinColumn(name = "vehicle", nullable = false)
    private Vehicle vehicle;
    @Column(name = "dateWithdrawal", nullable = false)
    private LocalDate dateWithdrawal;
    @Column(name = "dateDelivery", nullable = false)
    private LocalDate dateDelivery;
    @Column(name = "value_weekday", nullable = false)
    private int valueWeekday;
    @Column(name = "value_weekenday", nullable = false)
    private int valueWeekenday;
    @Column(name = "rentAmount")
    private int rentAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    public Rent(Vehicle vehicle, LocalDate dateWithdrawal, LocalDate dateDelivery, int valueWeekday, int valueWeekenday, int rentAmount, User user) {
        this.vehicle = vehicle;
        this.dateWithdrawal = dateWithdrawal;
        this.dateDelivery = dateDelivery;
        this.valueWeekday = valueWeekday;
        this.valueWeekenday = valueWeekenday;
        this.rentAmount = rentAmount;
        this.user = user;
    }

    public Rent(RentDto rentDto) {
        this.id = rentDto.getId();
        this.vehicle = new Vehicle();
        this.dateWithdrawal = rentDto.getDateWithdrawal();
        this.dateDelivery = rentDto.getDateDelivery();
        this.valueWeekday = rentDto.getValueWeekday();
        this.valueWeekenday = rentDto.getValueWeekenday();
        this.rentAmount = rentDto.getRentAmount();
        this.user = new User();
    }
}
