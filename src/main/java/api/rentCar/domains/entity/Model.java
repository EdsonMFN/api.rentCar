package api.rentCar.domains.entity;

import api.rentCar.domains.model.ModelDto;
import api.rentCar.enums.Category;
import api.rentCar.enums.Fabricator;
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

    @Column(name = "vehicleVersion", nullable = false)
    private String modelVersion;

    @Column(name = "modelYear", nullable = false)
    private Integer modelYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "fabricator", nullable = false)
    private Fabricator fabricator;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    public Model(String model,String modelVersion, Integer modelYear, Fabricator fabricator, Category category) {
        this.model = model;
        this.modelVersion = modelVersion;
        this.modelYear = modelYear;
        this.fabricator = fabricator;
        this.category = category;
    }
    public Model(ModelDto modelDto) {
        this.id = modelDto.getId();
        this.model = modelDto.getModel();
        this.modelVersion = modelDto.getModelVersion();
        this.modelYear = modelDto.getModelYear();
        this.fabricator = modelDto.getFabricator();
        this.category = modelDto.getCategory();
    }
}
