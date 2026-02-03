package com.feron.invoiceapi.invoice.api;

import com.feron.invoiceapi.invoice.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateInvoiceRequest request){
        service.createInvoice(request.invoiceId,request.amount);
    }

    @GetMapping("/{invoiceId}")
    public InvoiceAmountResponse getInvoice(@PathVariable String invoiceId){
        long amount = service.getInvoiceAmount(invoiceId);
        return new InvoiceAmountResponse(invoiceId,amount);
    }

    @GetMapping("/total")
    public TotalResponse getTotal(){
        return new TotalResponse(service.getTotalAmount());
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DeleteInvoiceResponse deleteInvoice(@PathVariable String invoiceId){
        service.deleteInvoice(invoiceId);
        return new DeleteInvoiceResponse(invoiceId,"Data Berhasil dihapus");
    }

}
