package com.feron.invoiceapi.invoice;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class InvoiceProcessor {
    private final Set<String> processedInvoiceIds = new HashSet<>();
    private final Map<String,Long> invoiceAmounts = new HashMap<>();

    public boolean markProcessed(String invoiceId){
        return  processedInvoiceIds.add(invoiceId);
    }

    public void saveAmount(String invoiceId, long amount){
        invoiceAmounts.put(invoiceId,amount);
    }

    public Long getAmount(String invoiceId){
        return invoiceAmounts.getOrDefault(invoiceId,0L);
    }

    public boolean exists(String invoiceId){return invoiceAmounts.containsKey(invoiceId);}

    public long getTotalAmount(){
        long total = 0L;
        for (Long amount : invoiceAmounts.values()){
            total += amount;
        }
        return total;
    }
}