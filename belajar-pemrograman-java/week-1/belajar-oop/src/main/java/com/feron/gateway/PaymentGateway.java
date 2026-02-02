package com.feron.gateway;

import com.feron.utils.PaymentReceipt;
import com.feron.utils.PaymentStatus;

public interface PaymentGateway {
    PaymentReceipt charge(String invoiceId, long amount);
    PaymentReceipt refund(String invoiceId, long amount);
    PaymentStatus status(String invoiceId);
}
