package com.feron.utils;

public class Invoice {
    public String invoiceId;
    public long amount;

    public Invoice(String invoiceId, long amount) {
        this.invoiceId = invoiceId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "invoiceId=" + invoiceId + ", amount=" + amount;
    }


}
