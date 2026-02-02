package com.feron.main;

import com.feron.utils.InvoiceProcessor;

public class AppDay2 {
    public static void main(String[] args) {
        InvoiceProcessor p = new InvoiceProcessor();

        System.out.println(p.markProcessed("INV-1")); // true
        System.out.println(p.markProcessed("INV-1")); // false

        p.saveAmount("INV-1", 100_000);
        p.saveAmount("INV-2", 200_000);

        System.out.println(p.getAmount("INV-1")); // 100000
        System.out.println(p.getAmount("INV-X")); // 0
        System.out.println(p.getTotalAmount());   // 300000
    }
}
