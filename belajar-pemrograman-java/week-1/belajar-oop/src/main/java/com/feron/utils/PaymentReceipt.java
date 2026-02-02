package com.feron.utils;

public class PaymentReceipt {
    public final String provider;
    public final String invoiceId;
    public final long amount;
    public final String status;

    public PaymentReceipt(String provider, String invoiceId, long amount, String status) {
        this.provider = provider;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentReceipt{" +
                "provider='" + provider + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                "}";
    }
}
