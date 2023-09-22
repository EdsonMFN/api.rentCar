package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private RepositoryVehicle repositoryVehicle;
    @Autowired
    private RepositoryModel repositoryModel;

    public ResponseVehicle createVehicle(RequestVehicle requestVehicle,String nameModel){

       try {
           Model model = Optional.of(repositoryModel.findByModel(nameModel))
                   .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with name "+ nameModel +" not found"));

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

       }catch (HandlerEntitydadeNotFoundException ex){
        throw new HandlerEntitydadeNotFoundException(ex.getMessage());
        }
        catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public List<ResponseVehicle> listVehicle(){

      try {
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

      }catch (Exception ex){
          throw new HandlerErrorException(ex.getMessage());
      }

    }
    public List<ResponseVehicle> searchByCategory(Integer category) {

        try {
            List<Model> models = Optional.of(repositoryModel.findAllByCategory(category))
                    .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with category "+ category +" not found"));

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
        }catch (HandlerEntitydadeNotFoundException ex){
            throw new HandlerEntitydadeNotFoundException(ex.getMessage());
        }
        catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }

    public ResponseVehicle delete(Long idVehicle){

        Vehicle vehicle = repositoryVehicle.findById(idVehicle)
                .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ idVehicle +" not found"));
        try {
            repositoryVehicle.deleteById(vehicle.getId());

            ResponseVehicle responseVehicle = new ResponseVehicle();
            return  responseVehicle;

        }catch (DataIntegrityViolationException ex){
            throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
}