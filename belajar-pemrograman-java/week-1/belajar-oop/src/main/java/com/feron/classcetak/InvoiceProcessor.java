package com.feron.classcetak;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvoiceProcessor {

    private final Set<String> processedInvoiceIds = new HashSet<>();
    private final Map<String,Long> invoiceAmounts  = new HashMap<>();

    public boolean markProcessed(String invoiceId){
        return  processedInvoiceIds.add(invoiceId);
    }

    public void saveAmount(String invoiceId, long amount){
        invoiceAmounts.put(invoiceId,amount);
    }

    public long getAmount(String invoiceId){
        return invoiceAmounts.getOrDefault(invoiceId, 0L);
    }

    public long getTotalAmount(){
        long totalAmount = 0L;
        for (long amount: invoiceAmounts.values()){
            totalAmount += amount;
        }
        return totalAmount;
    }

}
