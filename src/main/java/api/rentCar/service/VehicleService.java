package api.rentCar.service;

import api.rentCar.entity.Model;
import api.rentCar.entity.Vehicle;
import api.rentCar.repository.RepositoryVehicle;
import api.rentCar.repository.RepositoryModel;
import api.rentCar.rest.dto.ModelDto;
import api.rentCar.rest.dto.VehicleDto;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private RepositoryVehicle repositoryVehicle;
    @Autowired
    private RepositoryModel repositoryModel;

    public ResponseVehicle createVehicle(RequestVehicle requestVehicle){

        Model model = repositoryModel.getReferenceByModel(requestVehicle.getModel());

        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(requestVehicle.getPlate());
        vehicle.setModel(model);
        repositoryVehicle.save(vehicle);

        ModelDto modelDto = ModelDto.builder()
                .id(model.getId())
                .model(model.getModel())
                .modelYear(model.getModelYear())
                .fabricator(model.getFabricator())
                .category(model.getCategory())
                .build();

        return new ResponseVehicle(VehicleDto.builder()
                .id(vehicle.getId())
                .modelDto(modelDto)
                .plate(vehicle.getPlate())
                .build());
    }
    public List<ResponseVehicle> listVehicle(){

       List <Vehicle> vehicles = repositoryVehicle.findAll();
       List<ResponseVehicle> responseVehicles = new ArrayList<>();


        vehicles.forEach( vehicle -> {

            var model = vehicle.getModel();

            ModelDto modelDto = ModelDto.builder()
                    .id(model.getId())
                    .model(model.getModel())
                    .modelYear(model.getModelYear())
                    .fabricator(model.getFabricator())
                    .category(model.getCategory())
                    .build();

           ResponseVehicle responseVehicle =new ResponseVehicle (VehicleDto.builder()
                   .id(vehicle.getId())
                   .modelDto(modelDto)
                   .plate(vehicle.getPlate())
                   .build());

           responseVehicles.add(responseVehicle);
       });
       return responseVehicles;
    }
    public List<ResponseVehicle> searchByCategory(Integer category) {

        List<Model> models = repositoryModel.findAllByCategory(category);
        List<ResponseVehicle> responseVehicles = new ArrayList<>();

        List<Vehicle> vehicles = new ArrayList<>();

        models.forEach(model -> {

            List<Vehicle> vehiclesLambd = vehicles;
            vehiclesLambd = repositoryVehicle.findByModel(model);

            vehicles.addAll(vehiclesLambd);
        });

        vehicles.forEach(vehicle -> {
            var model = vehicle.getModel();

            ModelDto modelDto = ModelDto.builder()
                    .id(model.getId())
                    .model(model.getModel())
                    .modelYear(model.getModelYear())
                    .fabricator(model.getFabricator())
                    .category(model.getCategory())
                    .build();

            ResponseVehicle responseVehicle = new ResponseVehicle(VehicleDto.builder()
                    .id(vehicle.getId())
                    .modelDto(modelDto)
                    .plate(vehicle.getPlate())
                    .build());

            responseVehicles.add(responseVehicle);
        });
        return responseVehicles;

    }
    public ResponseVehicle delete(Long idVehicle){

        repositoryVehicle.deleteById(idVehicle);

        ResponseVehicle responseVehicle = new ResponseVehicle();

        return responseVehicle;
    }
}
