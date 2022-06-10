package ro.orange.uberpayment.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.orange.uberpayment.controllers.model.request.PaymentRequestModel;
import ro.orange.uberpayment.controllers.model.response.PaymentResponseModel;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${UBER_BACKEND_URL}")
    private String UBER_BACKEND_URL;

    private static final String PAYMENT_TYPE_AUTH = "AUTH";
    private static final String PAYMENT_TYPE_CONFIRM = "CONFIRM";

    public void authorize(PaymentRequestModel requestModel) {
        log.info("Payment Authorization Request: " + requestModel.toString());

        //process payment auth request asynchronously here
        CompletableFuture.supplyAsync(() -> notifyUber(requestModel, PAYMENT_TYPE_AUTH));
    }

    public void confirm(PaymentRequestModel requestModel) {
        log.info("Payment Confirmation Request: " + requestModel.toString());

        //process payment confirm request asynchronously here
        CompletableFuture.supplyAsync(() -> notifyUber(requestModel, PAYMENT_TYPE_CONFIRM));
    }

    public boolean notifyUber(PaymentRequestModel requestModel, String paymentType) {
        //10 seconds delay in the response
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //process the payment
        PaymentResponseModel paymentResponseModel = new PaymentResponseModel().bind(requestModel);
        if (PAYMENT_TYPE_AUTH.equals(paymentType)) {
            paymentResponseModel.setPaymentStatus("STATUS_AUTHORIZED");
            paymentResponseModel.setStatusReason("Payment is authorized successfully");
        } else if (PAYMENT_TYPE_CONFIRM.equals(paymentType)) {
            paymentResponseModel.setPaymentStatus("STATUS_CONFIRMED");
            paymentResponseModel.setStatusReason("Payment is confirmed successfully");
        }

        log.info("Notifying Uber backend with " + paymentResponseModel.toString());
        //notify the uber backend about the payment status
        boolean isCallbackSuccess = restTemplate.postForObject(UBER_BACKEND_URL + "/api/trip/paymentCallback", paymentResponseModel, Boolean.class);
        return true;
    }

}
