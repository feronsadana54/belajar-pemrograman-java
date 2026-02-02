package com.feron.produk;

import com.feron.classcetak.PaymentStatus;
import com.feron.gateway.PaymentGateway;
import com.feron.classcetak.PaymentReceipt;

public class MandiriGateway implements PaymentGateway {

    @Override

    public PaymentReceipt charge(String invoiceId, long amount) {
        return new PaymentReceipt("MANDIRI",invoiceId,amount,"CHARGED");
    }

    @Override
    public PaymentReceipt refund(String invoiceId, long amount) {
        return new PaymentReceipt("MANDIRI",invoiceId,amount,"REFUNDED");
    }

    @Override
    public PaymentStatus status(String invoiceId) {
        return new PaymentStatus(invoiceId,"MANDIRI");
    }
}
