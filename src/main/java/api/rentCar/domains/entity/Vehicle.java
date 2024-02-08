package api.rentCar.domains.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Vehicle implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicle")
    @PrimaryKeyJoinColumns(value = {@PrimaryKeyJoinColumn})
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model", nullable = false)
    private Model model;

    @Column(name = "plate", nullable = false)
    private String plate;

    public Vehicle(Long id, Model model, String plate) {
        this.id = id;
        this.model = model;
        this.plate = plate;
    }

    public Vehicle(Model model, String plate) {
        this.model = model;
        this.plate = plate;
    }


}
