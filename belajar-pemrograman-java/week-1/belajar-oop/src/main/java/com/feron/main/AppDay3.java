package com.feron.main;

import com.feron.service.InvoiceService;
import com.feron.utils.InvoiceProcessor;

public class AppDay3 {
    public static void main(String[] args) {
        InvoiceProcessor processor = new InvoiceProcessor();
        InvoiceService service = new InvoiceService(processor);

        System.out.println("=== TEST 1: Create INV-1 100000 (SUKSES) ===");
        safeRun(() -> {
            service.createInvoice("INV-1", 100_000);
            System.out.println("OK: create INV-1");
        });

        System.out.println("\n=== TEST 2: Create INV-1 lagi (DUPLIKAT) ===");
        safeRun(() -> {
            service.createInvoice("INV-1", 100_000);
            System.out.println("OK: create INV-1 (harusnya tidak tampil)");
        });

        System.out.println("\n=== TEST 3: Create INV-2 amount 0 (INVALID) ===");
        safeRun(() -> {
            service.createInvoice("INV-2", 0);
            System.out.println("OK: create INV-2 (harusnya tidak tampil)");
        });

        System.out.println("\n=== TEST 4: Get amount INV-1 (SUKSES) ===");
        safeRun(() -> {
            long amount = service.getInvoiceAmount("INV-1");
            System.out.println("OK: INV-1 amount = " + amount);
        });

        System.out.println("\n=== TEST 5: Get amount INV-X (NOT FOUND) ===");
        safeRun(() -> {
            long amount = service.getInvoiceAmount("INV-X");
            System.out.println("OK: INV-X amount = " + amount + " (harusnya tidak tampil)");
        });

        System.out.println("\n=== TEST 6: Total Amount (SUKSES, harus 100000) ===");
        safeRun(() -> {
            long total = processor.getTotalAmount();
            System.out.println("OK: total = " + total);
        });
    }

    private static void safeRun(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getClass().getSimpleName() + " | " + e.getMessage());
        }
    }

}
