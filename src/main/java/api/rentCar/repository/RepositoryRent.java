package api.rentCar.repository;

import api.rentCar.entity.Rent;
import api.rentCar.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositoryRent extends JpaRepository<Rent,Long> {

    Rent findByVehicle(Vehicle vehicle);
}
