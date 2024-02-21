package vilksp.returnedService.model;

import jakarta.persistence.*;
import vilksp.returnedService.model.exception.CaseHandlerException;

import java.time.LocalDateTime;

import static vilksp.returnedService.model.constants.ExceptionMessages.CASE_SOLVED;

@Entity
public class PaymentCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseId")
    private Long caseId;

    @OneToOne
    @MapsId
    private Payment payment;

    private LocalDateTime createdAt;
    private boolean solved;

    private PaymentCase(Payment payment) {
        this.payment = payment;
        this.createdAt = LocalDateTime.now();
        this.solved = false;
    }

    public PaymentCase() {
    }

    public static PaymentCase create(Payment payment) {
        return new PaymentCase(payment);
    }

    public void solve() {
        if (isSolved()) throw new CaseHandlerException(CASE_SOLVED);
        this.solved = true;
    }

    public Payment getPayment() {
        return payment;
    }

    public Long getCaseId() {
        return caseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isSolved() {
        return solved;
    }

}
