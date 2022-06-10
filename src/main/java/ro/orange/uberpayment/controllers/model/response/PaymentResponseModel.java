package ro.orange.uberpayment.controllers.model.response;

import lombok.Data;
import ro.orange.uberpayment.controllers.model.request.PaymentRequestModel;

import java.math.BigDecimal;

@Data
public class PaymentResponseModel {

    private BigDecimal amount;
    private long tripId;
    private long driverId;
    private long customerId;
    private String paymentStatus;
    private String statusReason;

    public PaymentResponseModel() {

    }

    public PaymentResponseModel bind(PaymentRequestModel requestModel) {
        this.amount = requestModel.getAmount();
        this.tripId = requestModel.getTripId();
        this.driverId = requestModel.getDriverId();
        this.customerId = requestModel.getCustomerId();

        return this;
    }

    @Override
    public String toString() {
        return String.format("TripId: %s, DriverId: %s, CustomerId: %s, Amount %s, Status %s",
                tripId, driverId, customerId, amount.toPlainString(), paymentStatus);
    }

}
