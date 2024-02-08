package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPayment extends JpaRepository<Payment,Long> {
    ;
}
