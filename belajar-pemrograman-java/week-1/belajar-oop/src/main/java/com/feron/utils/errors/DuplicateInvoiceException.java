package com.feron.utils.errors;

public class DuplicateInvoiceException extends  RuntimeException{
    public DuplicateInvoiceException(){
        super("Invoice sudah ada / duplikat");
    }
}
