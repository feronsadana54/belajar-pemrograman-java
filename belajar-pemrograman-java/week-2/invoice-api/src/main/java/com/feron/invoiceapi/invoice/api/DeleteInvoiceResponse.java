package com.feron.invoiceapi.invoice.api;

public class DeleteInvoiceResponse {

    public String invoiceId;
    public String message;

    public DeleteInvoiceResponse(String invoiceId, String message) {
        this.invoiceId = invoiceId;
        this.message = message;
    }
}
