package api.rentCar.repository;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ModelRepositoryTest {

    @Autowired
    private RepositoryModel repository;


    @Test
    @DisplayName("Criar model")
    void salvarModel() throws Exception {

        Model model = new Model("Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);

        Model saveModel = repository.save(model);

        assertNotNull(saveModel);
        assertTrue(saveModel.getId() > 0);
    }

}
