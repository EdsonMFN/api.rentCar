package api.rentCar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "model")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_model")
    @PrimaryKeyJoinColumns(value = @PrimaryKeyJoinColumn )
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "modelYear")
    private Integer modelYear;

    @Column(name = "fabricator")
    private String fabricator;

    @Column(name = "category")
    private Integer category;


}
