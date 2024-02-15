package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.enums.Category;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntityNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponseVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private RepositoryVehicle repositoryVehicle;
    @Autowired
    private RepositoryModel repositoryModel;

    public ResponseVehicle createVehicle(RequestVehicle requestVehicle,Long idModel){
        Model model = repositoryModel.findById(idModel)
                .orElseThrow(() -> new HandlerEntityNotFoundException("entity with name "+ idModel +" not found"));
       try {
           Vehicle vehicle = new Vehicle(requestVehicle);
           vehicle.setPlate(requestVehicle.getPlate());
           vehicle.setModel(model);
           repositoryVehicle.save(vehicle);

           ModelDto modelDto = new ModelDto(model);

           return new ResponseVehicle(VehicleDto.builder()
                   .id(vehicle.getId())
                   .modelDto(modelDto)
                   .plate(vehicle.getPlate())
                   .color(vehicle.getColor())
                   .mileage(vehicle.getMileage())
                   .photo(vehicle.getPhoto())
                   .build());

       }catch (HandlerEntityNotFoundException ex){
        throw new HandlerEntityNotFoundException(ex.getMessage());
        }
        catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public List<ResponseVehicle> filterVehicle(TypesToSearch filter){
      try {
          List <Vehicle> vehicles = repositoryVehicle.findAll();
          List<ResponseVehicle> responseVehicles = new ArrayList<>();

          vehicles.stream().filter(vehicle ->
                          vehicle.getId().equals(filter.getId()) ||
                          vehicle.getColor().equals(filter.getColor()) ||
                          vehicle.getPlate().equals(filter.getPlate())
                  )
                  .forEach( vehicle -> {

              var model = vehicle.getModel();

              ModelDto modelDto = new ModelDto(model);

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
    public List<ResponseVehicle> searchByCategory(Category category) {
        try {
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

                ModelDto modelDto = new ModelDto(model);

                ResponseVehicle responseVehicle = new ResponseVehicle(VehicleDto.builder()
                        .id(vehicle.getId())
                        .modelDto(modelDto)
                        .plate(vehicle.getPlate())
                        .build());

                responseVehicles.add(responseVehicle);
            });
            return responseVehicles;
        }catch (HandlerEntityNotFoundException ex){
            throw new HandlerEntityNotFoundException(ex.getMessage());
        }
        catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public void delete(Long idVehicle){
        Vehicle vehicle = repositoryVehicle.findById(idVehicle)
                .orElseThrow(() -> new HandlerEntityNotFoundException("entity with id "+ idVehicle +" not found"));
        try {
            repositoryVehicle.deleteById(vehicle.getId());

        }catch (DataIntegrityViolationException ex){
            throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
}