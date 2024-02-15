package api.rentCar.service;


import api.rentCar.domains.entity.Address;
import api.rentCar.domains.entity.Client;
import api.rentCar.domains.entity.User;
import api.rentCar.domains.model.AddressDto;
import api.rentCar.domains.model.ClientDto;
import api.rentCar.domains.model.UserDto;
import api.rentCar.domains.repository.RepositoryAddress;
import api.rentCar.domains.repository.RepositoryClient;
import api.rentCar.domains.repository.RepositoryUser;
import api.rentCar.exceptions.handlers.HandlerEntityNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.RequestUser;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private RepositoryClient repositoryClient;
    @Autowired
    private RepositoryAddress repositoryAddress;
    @Autowired
    private RepositoryUser repositoryUser;

    public ResponseUser createUser(RequestUser requestUser){

        var clientRequest = requestUser.getClient();
        var addressRequest = clientRequest.getAddress();
        try {
            Address address = new Address(addressRequest);
            repositoryAddress.save(address);

            Client client = new Client(clientRequest);
            client.setRg(rgFormat(clientRequest.getRg()));
            client.setPhone(phoneFormat(clientRequest.getPhone()));
            client.setAddress(address);
            repositoryClient.save(client);

            User user = new User(requestUser);
            user.setClient(client);
            repositoryUser.save(user);

            return new ResponseUser("Create user successfully");
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }

    }
    public List<ResponseUser> findByFilter(TypesToSearch filter){
        List<User> users = repositoryUser.findAll();
        List<ResponseUser> responses = new ArrayList<>();
        try {
            users.stream()
                    .filter(user ->
                            user.getActive().equals(filter.getDisabled()) ||
                            user.getClient().getName().toLowerCase().contains(filter.getName().toLowerCase()) ||
                            user.getId().equals(filter.getId()) ||
                            filter.getCpf() != null && user.getClient().getCpf().contains(filter.getCpf()))
                    .forEach(user -> {
                var patient = user.getClient();
                var address = patient.getAddress();

                AddressDto addressDto = new AddressDto(address);

                ClientDto clientDto = new ClientDto(patient);
                clientDto.setAddress(addressDto);

                ResponseUser responseUser = new ResponseUser(UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .client(clientDto)
                        .password(user.getPassword())
                        .username(user.getUsername())
                        .build());
                responses.add(responseUser);
            });
            return responses;

        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public ResponseUser findByUser(Long idUser){
        User user = repositoryUser.findById(idUser)
                .orElseThrow(()->new HandlerEntityNotFoundException("User not found whith id"+idUser));
        var patient = user.getClient();
        var address = patient.getAddress();
        try {
            AddressDto addressDto = new AddressDto(address);

            ClientDto clientDto = new ClientDto(patient);
            clientDto.setAddress(addressDto);
            return new ResponseUser(UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .client(clientDto)
                    .password(user.getPassword())
                    .username(user.getUsername())
                    .build());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public ResponseUser updateUser(RequestUser requestUser, Long idUser){
        User user = repositoryUser.findById(idUser)
                .orElseThrow(()->new HandlerEntityNotFoundException("User not found whith id"+idUser));
        var clientRequest = requestUser.getClient();
        var addressRequest = clientRequest.getAddress();
        try {
            Address address = new Address(addressRequest);
            repositoryAddress.save(address);

            Client client = new Client(clientRequest);
            client.setRg(rgFormat(clientRequest.getRg()));
            client.setPhone(phoneFormat(clientRequest.getPhone()));
            client.setAddress(address);
            repositoryClient.save(client);

            user.setUsername(requestUser.getUsername());
            user.setPassword(requestUser.getPassword());
            user.setEmail(requestUser.getEmail());
            user.setClient(client);
            repositoryUser.save(user);

            return new ResponseUser("Update user successfully");
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public ResponseUser deleteUser(Long idUser){
        User user = repositoryUser.findById(idUser)
                .orElseThrow(()->new HandlerEntityNotFoundException("User not found whith id"+idUser));
        try {
                repositoryUser.delete(user);

            return new ResponseUser("User delete successfully");
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public ResponseUser disableUser(Long idUser){
        User user = repositoryUser.findById(idUser)
                .orElseThrow(()->new HandlerEntityNotFoundException("User not found whith id"+idUser));
        try {
            if (user.getActive()){
                user.setActive(false);
                repositoryUser.save(user);
                return new ResponseUser("User successfully deactivated");
            }else {
                user.setActive(true);
                repositoryUser.save(user);
                return new ResponseUser("User successfully active");
            }
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    private String phoneFormat(String phone){
        if (phone.length() >= 11 && phone.matches("\\d+")){
            return String.format("(%s)%s",
                    phone.substring(0,2),
                    phone.substring(2,11));
        }else {
            throw new HandlerErrorException("Invalid phone number, enter numbers only!");
        }
    }
    private String rgFormat (String rg){
        if (rg.length() >= 7 && rg.matches("\\d+")){
            return String.format("%s.%s.%s",
                    rg.charAt(0),
                    rg.substring(1,4),
                    rg.substring(4,7));
        }else {
            throw new HandlerErrorException("Invalid rg number, enter numbers only!");
        }
    }
}
