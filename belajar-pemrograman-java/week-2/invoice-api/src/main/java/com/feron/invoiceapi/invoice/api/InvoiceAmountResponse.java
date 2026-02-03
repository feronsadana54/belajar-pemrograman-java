package com.feron.invoiceapi.invoice.api;

public class InvoiceAmountResponse {
    public String invoiceId;
    public long amount;

    public InvoiceAmountResponse(String invoiceId, long amount) {
        this.invoiceId = invoiceId;
        this.amount = amount;
    }
}
