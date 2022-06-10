package ro.orange.uberpayment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.orange.uberpayment.controllers.model.request.PaymentRequestModel;
import ro.orange.uberpayment.services.PaymentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorize")
    public ResponseEntity<Boolean> authorizePayment(@Valid @RequestBody PaymentRequestModel requestModel) {
        paymentService.authorize(requestModel);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Boolean> confirmPayment(@Valid @RequestBody PaymentRequestModel requestModel) {
        paymentService.confirm(requestModel);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }

}

