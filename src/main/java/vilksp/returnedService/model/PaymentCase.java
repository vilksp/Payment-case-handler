package vilksp.returnedService.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

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

    public PaymentCase(Long l, Payment eur) {
    }

    public PaymentCase() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Payment getReturnedPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Long getCaseId() {
        return caseId;
    }
}
