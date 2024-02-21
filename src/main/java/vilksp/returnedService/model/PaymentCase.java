package vilksp.returnedService.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
        if (isSolved()) throw new RuntimeException();
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
