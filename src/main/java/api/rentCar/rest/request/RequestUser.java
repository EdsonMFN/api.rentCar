package api.rentCar.rest.request;


import api.rentCar.domains.model.ClientDto;
import api.rentCar.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUser {

    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private String email;
    private Boolean active = true;
    private ClientDto client;
}
