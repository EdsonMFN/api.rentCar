package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRent extends JpaRepository<Rent,Long> {

    Rent findByVehicle(Vehicle vehicle);
}
