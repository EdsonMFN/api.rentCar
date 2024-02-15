package api.rentCar.domains.model;

import api.rentCar.domains.entity.Model;
import api.rentCar.enums.Category;
import api.rentCar.enums.Fabricator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelDto {

    private Long id;
    private String model;
    private String modelVersion;
    private Integer modelYear;
    private Fabricator fabricator;
    private Category category;

    public ModelDto(Model model) {
        this.id = model.getId();
        this.model = model.getModel();
        this.modelVersion = model.getModelVersion();
        this.modelYear = model.getModelYear();
        this.fabricator = model.getFabricator();
        this.category = model.getCategory();
    }
}
