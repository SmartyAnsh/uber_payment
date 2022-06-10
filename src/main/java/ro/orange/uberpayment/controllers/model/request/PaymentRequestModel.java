package ro.orange.uberpayment.controllers.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentRequestModel {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private long tripId;

    @NotNull
    private long driverId;

    @NotNull
    private long customerId;

    @Override
    public String toString() {
        return String.format("TripId: %s, DriverId: %s, CustomerId: %s, Amount %s", tripId, driverId, customerId, amount.toPlainString());
    }


}
