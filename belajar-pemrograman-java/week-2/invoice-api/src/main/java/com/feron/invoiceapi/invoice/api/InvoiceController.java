package com.feron.invoiceapi.invoice.api;

import com.feron.invoiceapi.invoice.api.dto.*;
import com.feron.invoiceapi.invoice.api.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateInvoiceRequest request){
        service.createInvoice(request.getInvoiceId(),request.getAmount());
    }

    @GetMapping("/{invoiceId}")
    public InvoiceAmountResponse getInvoice(@PathVariable String invoiceId){
        long amount = service.getInvoiceAmount(invoiceId);
        return new InvoiceAmountResponse(invoiceId,amount);
    }

    @GetMapping
    public List<InvoiceAmountResponse> getAllInvoices(){
        List<InvoiceAmountResponse> data = service.findAll();
        return data;
    }

    @GetMapping("/total")
    public TotalResponse getTotal(){
        return new TotalResponse(service.getTotalAmount());
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable String invoiceId){
        service.deleteInvoice(invoiceId);
    }

    @PutMapping("/{invoiceId}")
    public InvoiceAmountResponse updateInvoice(@Valid @RequestBody UpdateInvoiceRequest request, @PathVariable String invoiceId){
        long update = service.updateInvoice(request.getAmount(),invoiceId);
        return new InvoiceAmountResponse(invoiceId.trim(),update);
    }
}
