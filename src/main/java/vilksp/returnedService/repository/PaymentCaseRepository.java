package vilksp.returnedService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vilksp.returnedService.model.PaymentCase;

@Repository
public interface PaymentCaseRepository extends JpaRepository<PaymentCase, Long> {

    @Query(value = "SELECT COUNT(*)\n" +
            "FROM PAYMENT_CASE\n" +
            "INNER JOIN PAYMENT ON PAYMENT_CASE.PAYMENT_ID = PAYMENT.ID\n" +
            "WHERE PAYMENT.SOLVED=FALSE", nativeQuery = true)
    Long countOfActiveCases();
}
