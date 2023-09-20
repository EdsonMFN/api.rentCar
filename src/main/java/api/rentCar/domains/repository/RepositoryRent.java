package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryRent extends JpaRepository<Rent,Long> {

    Rent findByVehicle(Vehicle vehicle);
}
