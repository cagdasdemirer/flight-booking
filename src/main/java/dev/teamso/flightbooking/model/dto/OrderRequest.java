package dev.teamso.flightbooking.model.dto;

import javax.validation.constraints.NotNull;

public class OrderRequest{
    @NotNull(message = "Payment is mandatory")
    private boolean payment;

    public OrderRequest(boolean payment) {
        this.payment = payment;
    }

    public OrderRequest() {
    }

    public boolean getPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }
}
