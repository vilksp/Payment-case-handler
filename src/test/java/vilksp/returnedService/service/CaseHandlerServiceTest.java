package vilksp.returnedService.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vilksp.returnedService.model.PaymentType;
import vilksp.returnedService.model.ResolutionStatus;
import vilksp.returnedService.model.constants.ExceptionMessages;
import vilksp.returnedService.model.exception.CaseHandlerException;
import vilksp.returnedService.payload.CaseSolvingRequest;
import vilksp.returnedService.payload.PaymentRequest;
import vilksp.returnedService.repository.FakePaymentCaseRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CaseHandlerServiceTest {

    @Autowired
    private CaseHandlerService service;

    @BeforeEach
    void setUp() {
        var repo = new FakePaymentCaseRepository();
        service = new CaseHandlerService(repo);
    }

    @Test
    void createNewCase() {

        var returnedPaymentCase = service.createNewCase(new PaymentRequest(1l, BigDecimal.valueOf(102.2), "EUR", PaymentType.NORMAL));

        assertEquals(returnedPaymentCase.getReturnedPayment().getPaymentId(), 1L);
    }

    @Test
    void solveCase() {
        service.createNewCase(new PaymentRequest(1l, BigDecimal.valueOf(102.2), "EUR", PaymentType.NORMAL));

        var solvedCase = service.solveCase(new CaseSolvingRequest(1L, ResolutionStatus.RETURN));

        assertEquals(solvedCase.getReturnedPayment().getStatus(), ResolutionStatus.RETURN);
    }

    @Test
    void exceptionIsThrownWhenReturnedPaymentIsReturnedAgain() {
        service.createNewCase(new PaymentRequest(1l, BigDecimal.valueOf(102.2), "EUR", PaymentType.RETURNED));

        var exception = assertThrows(CaseHandlerException.class, () -> {
            service.solveCase(new CaseSolvingRequest(1L, ResolutionStatus.RETURN));

        });
        assertEquals(exception.getMessage(), ExceptionMessages.INVALID_RESOLUTION);
    }

    @Test
    void exceptionIsThrownWithWrongArguments() {
        service.createNewCase(new PaymentRequest(1l, BigDecimal.valueOf(102.2), "EUR", PaymentType.RETURNED));

        var exception = assertThrows(CaseHandlerException.class, () -> {
            service.solveCase(new CaseSolvingRequest(-100L, ResolutionStatus.NONE));

        });
        assertEquals(exception.getMessage(), ExceptionMessages.INVALID_ARGUMENTS);
    }

    @Test
    void exceptionIsThrownWhenCaseIsResubmitted() {
        service.createNewCase(new PaymentRequest(1l, BigDecimal.valueOf(102.2), "EUR", PaymentType.NORMAL));

        service.solveCase(new CaseSolvingRequest(1L, ResolutionStatus.RESUBMIT));

        var exception = assertThrows(CaseHandlerException.class, () -> {
            service.solveCase(new CaseSolvingRequest(1L, ResolutionStatus.RESUBMIT));

        });
        assertEquals(exception.getMessage(), ExceptionMessages.SOLVED_CASE);
    }


    @Test
    void countOfActiveCases() {
        service.createNewCase(new PaymentRequest(1l, BigDecimal.valueOf(102.2), "EUR", PaymentType.NORMAL));

        var countOfCases = service.countOfActiveCases();

        assertEquals(countOfCases, 1L);

        service.solveCase(new CaseSolvingRequest(1L, ResolutionStatus.RETURN));

        countOfCases = service.countOfActiveCases();

        assertEquals(countOfCases, 0L);
    }
}