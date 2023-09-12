package api.rentCar.repository;

import api.rentCar.entity.Model;
import api.rentCar.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositoryVehicle extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByModel(Model model);

    Vehicle findByPlate(String plate);
}
