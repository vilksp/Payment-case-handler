package vilksp.returnedService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private Long paymentId;

    private BigDecimal amount;
    private String currency;
    private PaymentType type;

    private boolean solved;

    private ResolutionStatus status;

    public Payment(Long paymentId, BigDecimal amount, String currency, PaymentType type) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.solved = false;
        this.status = ResolutionStatus.NONE;
    }

    public Payment() {
    }

    public void setStatus(ResolutionStatus status) {
        this.status = status;
    }

    public PaymentType getType() {
        return type;
    }

    public ResolutionStatus getStatus() {
        return status;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Long getId() {
        return id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
