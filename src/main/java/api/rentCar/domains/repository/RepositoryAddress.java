package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAddress extends JpaRepository<Address,Long> {
}
