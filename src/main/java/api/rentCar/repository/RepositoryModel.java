package api.rentCar.repository;

import api.rentCar.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryModel extends JpaRepository<Model,Long> {

    Model findByModel(String model);


    List<Model> findAllByCategory(Integer category);

    Model getReferenceByModel(String modelName);
}
