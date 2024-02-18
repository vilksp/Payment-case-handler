package vilksp.returnedService.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import vilksp.returnedService.model.PaymentType;
import vilksp.returnedService.model.ResolutionStatus;
import vilksp.returnedService.payload.CaseSolvingRequest;
import vilksp.returnedService.payload.PaymentRequest;
import vilksp.returnedService.repository.FakePaymentCaseRepository;
import vilksp.returnedService.service.CaseHandlerService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static vilksp.returnedService.model.constants.ExceptionMessages.NOT_FOUND_CASE;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CaseControllerTest {
    @Autowired
    private CaseHandlerService service;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        this.service = new CaseHandlerService(new FakePaymentCaseRepository());
    }

    @Test
    void createPaymentCase() throws JSONException {
        PaymentRequest pr = new PaymentRequest(1L, BigDecimal.valueOf(10123.22), "EUR", PaymentType.NORMAL);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<PaymentRequest> he = new HttpEntity<>(pr, headers);

        ResponseEntity<?> response = restTemplate.postForEntity("/api/v1/case/create", he, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody().toString());

        assertEquals(response.getStatusCode().value(), 201);

        var returnedPayment = jsonObject.get("returnedPayment");
        assertEquals(((JSONObject) returnedPayment).get("currency"), "EUR");
        assertEquals(((JSONObject) returnedPayment).get("amount"), 10123.22);
    }

    @Test
    void solvePaymentCase() throws JSONException {
        PaymentRequest pr = new PaymentRequest(1L, BigDecimal.valueOf(10123.22), "EUR", PaymentType.NORMAL);
        HttpHeaders hh = new HttpHeaders();
        HttpEntity<PaymentRequest> httpEntity = new HttpEntity<>(pr, hh);
        restTemplate.postForEntity("/api/v1/case/create", httpEntity, String.class);

        HttpHeaders headers = new HttpHeaders();
        CaseSolvingRequest request = new CaseSolvingRequest(1L, ResolutionStatus.RESUBMIT);
        HttpEntity<CaseSolvingRequest> he = new HttpEntity<>(request, headers);
        ResponseEntity<?> response = restTemplate.postForEntity("/api/v1/case/solve", he, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody().toString());
        var returnedPayment = jsonObject.get("returnedPayment");
        assertEquals(((JSONObject) returnedPayment).get("status"), "RESUBMIT");
    }

    @Test
    void exceptionIsThrownWhenReturnedPaymentIsReturnedAgain() {
        PaymentRequest pr = new PaymentRequest(1L, BigDecimal.valueOf(10123.22), "EUR", PaymentType.RETURNED);
        HttpHeaders hh = new HttpHeaders();
        HttpEntity<PaymentRequest> httpEntity = new HttpEntity<>(pr, hh);
        restTemplate.postForEntity("/api/v1/case/create", httpEntity, String.class);
    }

    @Test
    void getCountOfActiveCases() {
        PaymentRequest pr = new PaymentRequest(1L, BigDecimal.valueOf(10123.22), "EUR", PaymentType.NORMAL);
        HttpHeaders hh = new HttpHeaders();
        HttpEntity<PaymentRequest> httpEntity = new HttpEntity<>(pr, hh);
        restTemplate.postForEntity("/api/v1/case/create", httpEntity, String.class);

        var response = restTemplate.getForEntity("/api/v1/case/activeCases", Long.class);

        assertEquals(response.getBody(), 1L);
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    void getExceptionWhenSolvingNonExistingCase() {
        HttpHeaders headers = new HttpHeaders();
        CaseSolvingRequest request = new CaseSolvingRequest(1L, ResolutionStatus.RESUBMIT);
        HttpEntity<CaseSolvingRequest> he = new HttpEntity<>(request, headers);
        ResponseEntity<?> response = restTemplate.postForEntity("/api/v1/case/solve", he, String.class);

        assertEquals(response.getStatusCode().value(),400);
        assertEquals(response.getBody(),NOT_FOUND_CASE);
    }
}