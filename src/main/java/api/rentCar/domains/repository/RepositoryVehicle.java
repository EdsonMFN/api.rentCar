package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RepositoryVehicle extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByModel(Model model);

    Vehicle findByPlate(String plate);
}
