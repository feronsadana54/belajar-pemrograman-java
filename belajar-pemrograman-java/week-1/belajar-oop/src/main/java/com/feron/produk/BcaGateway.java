package com.feron.produk;

import com.feron.utils.PaymentReceipt;
import com.feron.utils.PaymentStatus;
import com.feron.gateway.PaymentGateway;

public class BcaGateway implements PaymentGateway {

    @Override
    public PaymentReceipt charge(String invoiceId, long amount){
        return new PaymentReceipt("BCA",invoiceId,amount,"CHARGED");
    }

    @Override
    public PaymentReceipt refund(String invoiceId, long amount){
        return new PaymentReceipt("BCA",invoiceId,amount,"REFUNDED");
    }

    @Override
    public PaymentStatus status(String invoiceId){
        return new PaymentStatus(invoiceId,"BCA");
    }
}
