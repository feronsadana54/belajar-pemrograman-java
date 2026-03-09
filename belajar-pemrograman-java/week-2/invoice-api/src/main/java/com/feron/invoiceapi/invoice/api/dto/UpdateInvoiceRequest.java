package com.feron.invoiceapi.invoice.api.dto;

import jakarta.validation.constraints.Positive;

public class UpdateInvoiceRequest {

    @Positive(message = "amount harus lebih besar dari 0")
    public long amount;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
