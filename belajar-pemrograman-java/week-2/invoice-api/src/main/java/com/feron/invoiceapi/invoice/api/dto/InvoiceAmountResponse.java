package com.feron.invoiceapi.invoice.api.dto;

public class InvoiceAmountResponse {
    public String invoiceId;
    public long amount;

    public InvoiceAmountResponse(String invoiceId, long amount) {
        this.invoiceId = invoiceId;
        this.amount = amount;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
