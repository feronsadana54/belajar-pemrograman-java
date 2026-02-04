package com.feron.invoiceapi.invoice.api;

import jakarta.validation.constraints.*;

public class CreateInvoiceRequest {

    @NotBlank(message = "invoiceId wajib diisi")
    @Size(min = 3, max = 20, message = "invoiceId tidak boleh kurang dari 3 dan tidak boleh lebih dari 20")
    public String invoiceId;

    @Positive(message = "amount harus > 0")
    public long amount;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = (invoiceId == null) ? null : invoiceId.trim();
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
