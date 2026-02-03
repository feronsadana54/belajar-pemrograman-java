package com.feron.invoiceapi;

import com.feron.invoiceapi.invoice.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDay4Runner implements CommandLineRunner {

    private final InvoiceService service;

    public TestDay4Runner(InvoiceService service){
        this.service = service;
    }

    @Override
    public void run(String... args){
        System.out.println("=== Days 4 Step 1 (DI Test)");

        safeRun(() -> {
            service.createInvoice("INV-1", 100_000);
            System.out.println("OK: create INV-1");
        });

        safeRun(() -> {
            service.createInvoice("INV-1", 100_000);
            System.out.println("OK: create INV-1 (harusnya tidak tampil)");
        });

        safeRun(() -> {
            System.out.println("OK: amount INV-1 = " + service.getInvoiceAmount("INV-1"));
        });

        safeRun(() -> {
            System.out.println("OK: amount INV-X = " + service.getInvoiceAmount("INV-X"));
        });

        safeRun(() -> {
            service.createInvoice("INV-2", 0);
            System.out.println("OK: create INV-2 (harusnya tidak tampil)");
        });

        safeRun(() -> {
            System.out.println("OK: total = " + service.getTotalAmount());
        });
    }

    public void safeRun(Runnable runnable){
        try{
            runnable.run();
        }catch (RuntimeException e){
            System.out.println("ERROR: " + e.getClass().getSimpleName() + " | " + e.getMessage());
        }
    }
}
