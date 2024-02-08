package api.rentCar.repository;


import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VehicleRepositoryTest {

    @Autowired
    private RepositoryVehicle repository;
    @Autowired
    private RepositoryModel repositoryModel;

    @Test
    @DisplayName("Quando o vehicle for salvo deve retornar o vehicle")
    void salvarVehicle() {

        Model model = new Model("Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);

        Model saveModel = repositoryModel.save(model);

        Vehicle vehicle = new Vehicle(saveModel,"rio2020");

        Vehicle saveVehicle = repository.save(vehicle);

        assertNotNull(saveVehicle);
        assertTrue(saveVehicle.getId() > 0);
    }
    @Test
    @DisplayName("buscar todos vehicle")
    void listarVehicle()  throws Exception {
        Model model1 = new Model("Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);
        Model model = new Model("Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);
        repositoryModel.save(model1);
        repositoryModel.save(model);

        Vehicle vehicle1 = new Vehicle(model1,"rio2020");
        Vehicle vehicle = new Vehicle(model,"rio2020");

        repository.save(vehicle1);
        repository.save(vehicle);

        List<Vehicle> vehicleList = repository.findAll();

        assertNotNull(vehicleList);
        assertEquals(2,vehicleList.size());

    }
    @Test
    @DisplayName("buscar todos por category")
    void listarByCategoryVehicle()  throws Exception {

    }
    @Test
    @DisplayName("buscar todos por category")
    void listarByCategoryNotFoundVehicle()  throws Exception {


    }
    @Test
    @DisplayName("Excluir veículo por ID")
    void deleteVehicle()  throws  Exception {


    }
}
