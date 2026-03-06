package com.feron.invoiceapi.invoice.api.service;

import com.feron.invoiceapi.invoice.api.dto.InvoiceAmountResponse;
import com.feron.invoiceapi.invoice.errors.DuplicateInvoiceException;
import com.feron.invoiceapi.invoice.errors.InvalidAmountException;
import com.feron.invoiceapi.invoice.errors.InvoiceNotFoundException;
import com.feron.invoiceapi.invoice.api.repository.InvoiceRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    private final InvoiceRepository repo;

    public InvoiceService(InvoiceRepository repo) {
        this.repo = repo;
    }

    public void createInvoice(String invoiceId, long amount){
        String id = normalize(invoiceId);
        if (amount <=0 ) throw new InvalidAmountException(amount);
        if (id == null || id.isBlank())throw new IllegalArgumentException("invoiceId wajib diisi");

        try {
            repo.isInsert(id, amount);
        }catch (DuplicateKeyException e){
            throw new DuplicateInvoiceException(id);
        }
    }

    public long getInvoiceAmount(String invoiceId){
        String id = normalize(invoiceId);
        if (!repo.isExist(id)) throw new InvoiceNotFoundException(id);
        return repo.findAmount(id);
    }

    public long getTotalAmount(){
        return repo.findTotalAmount();
    }

    public void deleteInvoice(String invoiceId){
        String id = normalize(invoiceId);
        int deleted = repo.deleteInvoice(id);
        if (deleted == 0) throw new InvoiceNotFoundException(id);
    }

    public List<InvoiceAmountResponse> findAll(){
        return repo.findAll();
    }

    public String normalize(String invoiceId){
        return invoiceId == null ? null : invoiceId.trim();
    }
}
