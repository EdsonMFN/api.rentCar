package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositoryVehicle extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByModel(Model model);

    Vehicle findByPlate(String plate);
}
