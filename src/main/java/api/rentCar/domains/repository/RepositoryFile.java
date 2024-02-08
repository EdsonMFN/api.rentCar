package api.rentCar.domains.repository;

import api.rentCar.domains.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryFile extends JpaRepository<File,Long> {


}
