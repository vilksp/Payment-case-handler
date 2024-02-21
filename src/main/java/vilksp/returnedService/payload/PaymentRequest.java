package vilksp.returnedService.payload;

import vilksp.returnedService.model.Amount;
import vilksp.returnedService.model.PaymentType;

public record PaymentRequest(Long paymentId, Amount amount, PaymentType type) {

}
