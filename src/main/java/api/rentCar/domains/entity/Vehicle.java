package api.rentCar.domains.entity;

import api.rentCar.enums.ColorVehicle;
import api.rentCar.rest.request.RequestVehicle;
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

    @Column(name = "color", nullable = false)
    private ColorVehicle color;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "photo", nullable = false)
    private String photo;

    public Vehicle(Long id, Model model, String plate,ColorVehicle color,int mileage,String photo) {
        this.id = id;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.mileage = mileage;
        this.photo = photo;
    }

    public Vehicle(Model model, String plate,ColorVehicle color,int mileage,String photo) {
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.mileage = mileage;
        this.photo = photo;
    }
    public Vehicle(RequestVehicle requestVehicle) {
        this.id =requestVehicle.getId();
        this.model = new Model();
        this.plate = requestVehicle.getPlate();
        this.color = requestVehicle.getColor();
        this.mileage = requestVehicle.getMileage();
        this.photo = requestVehicle.getPhoto();
    }

}
