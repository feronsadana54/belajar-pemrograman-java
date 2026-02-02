package com.feron.service;


import com.feron.utils.InvoiceProcessor;
import com.feron.utils.errors.DuplicateInvoiceException;
import com.feron.utils.errors.InvalidAmountException;
import com.feron.utils.errors.InvoiceNotFoundException;

public class InvoiceService {
    private final InvoiceProcessor invoiceProcessor;

    public InvoiceService(InvoiceProcessor invoiceProcessor) {
        this.invoiceProcessor = invoiceProcessor;
    }

    public void createInvoice(String invoiceId,long amount){
        if (amount <= 0){
            throw new InvalidAmountException("amount tidak boleh lebih kecil sama dengan dari 0");
        }else if (!invoiceProcessor.markProcessed(invoiceId)){
            throw new DuplicateInvoiceException();
        }

        invoiceProcessor.saveAmount(invoiceId, amount);
    }

    public long getInvoiceAmount(String invoiceId){
        if (!invoiceProcessor.exists(invoiceId)) throw new InvoiceNotFoundException("Invoice tidak ditemukan");
        return invoiceProcessor.getAmount(invoiceId);
    }
}
