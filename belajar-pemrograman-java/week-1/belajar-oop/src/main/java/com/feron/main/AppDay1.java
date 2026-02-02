package com.feron.main;

import com.feron.gateway.PaymentGateway;
import com.feron.produk.BcaGateway;
import com.feron.produk.MandiriGateway;
import com.feron.service.PaymentService;

public class AppDay1 {
    public static void main(String[] args) {
        PaymentGateway mandiri = new MandiriGateway();
        PaymentService paymentServiceMandiri = new PaymentService(mandiri);
        PaymentGateway bca = new BcaGateway();
        PaymentService paymentServiceBca = new PaymentService(bca);

//        mandiri
        System.out.println("=== MANDIRI ===");
        safeRun(() -> System.out.println(paymentServiceMandiri.charge("A001",1_000_000)));
        safeRun(() -> System.out.println(paymentServiceMandiri.refund("RFN001",0)));
        safeRun(() -> System.out.println(paymentServiceMandiri.status("RFN001")));

        System.out.println("----------------------");

//        bca
        System.out.println("=== BCA ===");
        safeRun(() -> System.out.println(paymentServiceBca.charge("BC001",150_000)));
        safeRun(() -> System.out.println(paymentServiceBca.refund("BC001",12_000_000)));
        safeRun(() -> System.out.println(paymentServiceBca.status("BC001")));
    }

    private static void safeRun(Runnable action){
        try {
            action.run();
        }catch(IllegalArgumentException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
