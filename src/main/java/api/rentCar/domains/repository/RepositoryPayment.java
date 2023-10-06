package api.rentCar.domains.repository;

import api.rentCar.domains.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPayment extends JpaRepository<Payment,Long> {
    ;
}
