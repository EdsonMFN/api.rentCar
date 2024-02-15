package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Model;
import api.rentCar.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryModel extends JpaRepository<Model,Long> {

    Model findByModel(String model);

    List<Model> findAllByCategory(Category category);

}
