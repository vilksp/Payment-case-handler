package vilksp.returnedService.payload;

import vilksp.returnedService.model.PaymentType;

import java.math.BigDecimal;

public class PaymentRequest {

    private Long paymentId;
    private BigDecimal amount;
    private String currency;
    private PaymentType type;

    public PaymentRequest(Long paymentId, BigDecimal amount, String currency, PaymentType type) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
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

    public PaymentType getType() {
        return type;
    }
}
