package com.feron.gateway;

import com.feron.classcetak.PaymentReceipt;
import com.feron.classcetak.PaymentStatus;

public interface PaymentGateway {
    PaymentReceipt charge(String invoiceId, long amount);
    PaymentReceipt refund(String invoiceId, long amount);
    PaymentStatus status(String invoiceId, String provider);
}
