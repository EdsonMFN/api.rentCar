package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryClient extends JpaRepository<Client,Long> {
}
