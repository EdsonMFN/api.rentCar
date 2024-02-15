package api.rentCar.domains.repository;

import api.rentCar.domains.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUser extends JpaRepository<User,Long> {

}
