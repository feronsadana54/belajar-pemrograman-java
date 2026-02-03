package com.feron.invoiceapi.invoice;

import com.feron.invoiceapi.invoice.errors.DuplicateInvoiceException;
import com.feron.invoiceapi.invoice.errors.InvalidAmountException;
import com.feron.invoiceapi.invoice.errors.InvoiceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceProcessor processor;

    public InvoiceService(InvoiceProcessor processor) {
        this.processor = processor;
    }

    public void createInvoice(String invoiceId, long amount){
        if (amount <=0 ) throw new InvalidAmountException(amount);
        if (!processor.markProcessed(invoiceId)) throw new DuplicateInvoiceException(invoiceId);
        processor.saveAmount(invoiceId, amount);
    }

    public long getInvoiceAmount(String invoiceId){
        if (!processor.exists(invoiceId)) throw new InvoiceNotFoundException(invoiceId);
        return processor.getAmount(invoiceId);
    }

    public long getTotalAmount(){
        return processor.getTotalAmount();
    }

    public void deleteInvoice(String invoiceId){
        if (!processor.exists(invoiceId)) throw new InvoiceNotFoundException(invoiceId);
        processor.deleteInvoice(invoiceId);
    }

}
