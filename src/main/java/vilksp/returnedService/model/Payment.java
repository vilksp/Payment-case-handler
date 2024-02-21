package vilksp.returnedService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import vilksp.returnedService.model.exception.CaseHandlerException;

import static vilksp.returnedService.model.constants.ExceptionMessages.INVALID_RESOLUTION;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private Long paymentId;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private Amount amount;
    private PaymentType type;

    private ResolutionStatus status;

    private Payment(Long paymentId, Amount amount, PaymentType type) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.type = type;
        this.status = ResolutionStatus.NONE;
    }

    public Payment() {
    }

    public static Payment create(Long paymentId, Amount amount, PaymentType type) {
        return new Payment(paymentId, amount, type);
    }

    public void changeResolutionStatus(ResolutionStatus statusToChange) {
        if (statusToChange.equals(ResolutionStatus.NONE)) throw new CaseHandlerException(INVALID_RESOLUTION);
            this.status = statusToChange;
    }

    public Long getId() {
        return id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Amount getAmount() {
        return amount;
    }

    public PaymentType getType() {
        return type;
    }

    public ResolutionStatus getStatus() {
        return status;
    }
}
