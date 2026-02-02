package com.feron.utils;

public class PaymentStatus {
    public final String invoiceId;
    public final String provider;

    public PaymentStatus(String invoiceId, String provider) {
        this.invoiceId = invoiceId;
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "PaymentStatus {invoiceId=" + invoiceId +
                ", provider=" + provider +"}";
    }
}
