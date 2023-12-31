package api.rentCar.domains.entity;

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
    private Integer valueWeekday;

    @Column(name = "value_weekenday", nullable = false)
    private Integer valueWeekenday;

    @Column(name = "rentAmount")
    private Integer rentAmount;


}
