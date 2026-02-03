package com.feron.invoiceapi.invoice.errors;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(long amount){
        super("Amount tidak valid: "+ amount +" harus lebih dari > 0");
    }
}
