package com.feron.invoiceapi.invoice.errors;

public class InvoiceNotFoundException extends RuntimeException{
    public InvoiceNotFoundException(String invoiceId){
        super("Invoice tidak ditemukan: "+ invoiceId);
    }
}
