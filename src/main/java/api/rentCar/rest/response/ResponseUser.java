package api.rentCar.rest.response;


import api.rentCar.domains.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUser {
    private UserDto user;

    private String msg;

    public ResponseUser(UserDto user) {
        this.user = user;
    }

    public ResponseUser(String msg) {
        this.msg = msg;
    }
}
