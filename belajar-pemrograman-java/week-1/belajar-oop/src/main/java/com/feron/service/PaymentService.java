package com.feron.service;

import com.feron.classcetak.PaymentStatus;
import com.feron.gateway.PaymentGateway;
import com.feron.classcetak.PaymentReceipt;

public class PaymentService {
    private final PaymentGateway gateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.gateway = paymentGateway;
    }

    public PaymentReceipt charge(String invoiceId, long amount) {
        validateAmount(amount);
        return gateway.charge(invoiceId, amount);
    }

    public PaymentReceipt refund(String invoiceId, long amount) {
        validateAmount(amount);
        return gateway.refund(invoiceId, amount);
    }

    public PaymentStatus status(String invoiceId, String provider) {
        return gateway.status(invoiceId, provider);
    }

    private void validateAmount(long amount) {
        try{
            if (amount > 1_000_000) {
                throw new IllegalArgumentException("Amount tidak boleh lebih besar dari Rp.1.000.000");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount tidak boleh lebih kecil dari sama dengan Rp.0");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
