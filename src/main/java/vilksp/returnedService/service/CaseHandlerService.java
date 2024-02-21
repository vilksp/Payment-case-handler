package vilksp.returnedService.service;

import org.springframework.stereotype.Service;
import vilksp.returnedService.model.PaymentCase;
import vilksp.returnedService.model.Payment;
import vilksp.returnedService.model.PaymentType;
import vilksp.returnedService.model.ResolutionStatus;
import vilksp.returnedService.model.exception.CaseHandlerException;
import vilksp.returnedService.payload.CaseSolvingRequest;
import vilksp.returnedService.payload.PaymentRequest;
import vilksp.returnedService.repository.PaymentCaseRepository;

import static vilksp.returnedService.model.constants.ExceptionMessages.*;

@Service
public class CaseHandlerService {


    private final PaymentCaseRepository repository;


    public CaseHandlerService(PaymentCaseRepository repo) {
        this.repository = repo;
    }

    public PaymentCase createNewCase(PaymentRequest request) {
        if (request == null) throw new CaseHandlerException(INVALID_ARGUMENTS);
        return repository.save(PaymentCase.create(Payment.create(request.paymentId(), request.amount(), request.type())));
    }


    public PaymentCase solveCase(CaseSolvingRequest request) {
        if (request.getCaseId() < 0 || request.getStatus().equals(ResolutionStatus.NONE))
            throw new CaseHandlerException(INVALID_ARGUMENTS);

        var caseToFind = repository.findById(request.getCaseId());

        if (caseToFind.isEmpty()) throw new CaseHandlerException(NOT_FOUND_CASE);

        var currentCase = caseToFind.get();

        if (currentCase.isSolved()) throw new CaseHandlerException(SOLVED_CASE);

        if (currentCase.getPayment().getType().equals(PaymentType.NORMAL)) {
            return normalPaymentResolver(currentCase, request.getStatus());
        } else {
            return returnedPaymentResolver(currentCase, request.getStatus());
        }
    }


    private PaymentCase returnedPaymentResolver(PaymentCase currentCase, ResolutionStatus status) {
        if (status.equals(ResolutionStatus.RETURN))
            throw new CaseHandlerException(INVALID_RESOLUTION);
        currentCase.getPayment().changeResolutionStatus(status);
        currentCase.solve();
        return repository.save(currentCase);
    }

    private PaymentCase normalPaymentResolver(PaymentCase currentCase, ResolutionStatus status) {
        currentCase.getPayment().changeResolutionStatus(status);
        currentCase.solve();
        return repository.save(currentCase);
    }

    public long countOfActiveCases() {
        return repository.countOfActiveCases();
    }

}
