package com.feron.invoiceapi.invoice.errors;

public class DuplicateInvoiceException extends RuntimeException{
    public DuplicateInvoiceException(String invoiceId){
        super("Invoice duplikat: <"+invoiceId+">");
    }
}
