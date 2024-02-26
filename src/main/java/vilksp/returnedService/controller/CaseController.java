package vilksp.returnedService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vilksp.returnedService.model.PaymentCase;
import vilksp.returnedService.payload.CaseSolvingRequest;
import vilksp.returnedService.payload.PaymentRequest;
import vilksp.returnedService.service.CaseHandlerService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/")
public class CaseController {


    public final CaseHandlerService service;

    public CaseController(CaseHandlerService service) {
        this.service = service;
    }

    @PostMapping("/cases")
    public ResponseEntity<PaymentCase> createPaymentCase(@RequestBody PaymentRequest request) {
        return new ResponseEntity<>(service.createNewCase(request), CREATED);
    }

    @PostMapping("/cases/{id}/resolution")
    public ResponseEntity<PaymentCase> solvePaymentCase(@PathVariable Long id, @RequestBody CaseSolvingRequest request) {
        return new ResponseEntity(service.solveCase(id, request), OK);
    }

    @GetMapping("/activeCases")
    public ResponseEntity<Long> getCountOfActiveCases() {
        return new ResponseEntity<>(service.countOfActiveCases(), OK);
    }
}
