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
        String id = normalize(invoiceId);
        if (amount <=0 ) throw new InvalidAmountException(amount);
        if (!processor.markProcessed(id)) throw new DuplicateInvoiceException(id);
        processor.saveAmount(id, amount);
    }

    public long getInvoiceAmount(String invoiceId){
        String id = normalize(invoiceId);
        if (!processor.exists(id)) throw new InvoiceNotFoundException(id);
        return processor.getAmount(id);
    }

    public long getTotalAmount(){
        return processor.getTotalAmount();
    }

    public void deleteInvoice(String invoiceId){
        String id = normalize(invoiceId);
        if (!processor.exists(id)) throw new InvoiceNotFoundException(id);
        processor.deleteInvoice(id);
    }

    public String normalize(String invoiceId){
        return invoiceId == null ? null : invoiceId.trim();
    }
}
