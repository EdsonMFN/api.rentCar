package api.rentCar.entity;

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
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @Column(name = "dateWithdrawal")
    private LocalDate dateWithdrawal;

    @Column(name = "dateDelivery")
    private LocalDate dateDelivery;

    @Column(name = "value_weekday")
    private Integer valueWeekday;

    @Column(name = "value_weekenday")
    private Integer valueWeekenday;

    @Column(name = "rentAmount")
    private Integer rentAmount;

    public LocalDate dateWithdrawal(){
        return dateWithdrawal = LocalDate.now();
    }

}
