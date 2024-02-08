package api.rentCar.domains.entity;

import api.rentCar.enums.Category;
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

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "modelYear", nullable = false)
    private Integer modelYear;

    @Column(name = "fabricator", nullable = false)
    private String fabricator;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    public Model(String model, Integer modelYear, String fabricator, Category category) {
        this.model = model;
        this.modelYear = modelYear;
        this.fabricator = fabricator;
        this.category = category;
    }
}
