package api.rentCar.domains.repository;

import api.rentCar.domains.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryFile extends JpaRepository<File,Long> {


}
