package api.rentCar.domains.entity;

import api.rentCar.enums.UserRole;
import api.rentCar.rest.request.RequestUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "password",nullable = false,unique = true)
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Email
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "status")
    private Boolean active;
    @OneToOne
    @JoinColumn(name = "id_client")
    private Client client;

    public User(RequestUser requestUser) {
        this.id = requestUser.getId();
        this.username = requestUser.getUsername();
        this.password = requestUser.getPassword();
        this.role = requestUser.getRole();
        this.email = requestUser.getEmail();
        this.active = requestUser.getActive();
        this.client = new Client();
    }
}
