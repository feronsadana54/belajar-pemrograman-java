package com.feron.invoiceapi.invoice;

import com.feron.invoiceapi.invoice.api.InvoiceAmountResponse;
import com.feron.invoiceapi.invoice.errors.DuplicateInvoiceException;
import com.feron.invoiceapi.invoice.errors.InvalidAmountException;
import com.feron.invoiceapi.invoice.errors.InvoiceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceServiceTest {

    private InvoiceService service;

    @BeforeEach
    void setUp(){
        InvoiceProcessor processor = new InvoiceProcessor();
        service = new InvoiceService(processor);
    }

    @Test
    void createInvoiceSuccess(){
        String invoiceId = "INV-1";
        long amount = 100_000;

        service.createInvoice(invoiceId,amount);

        assertEquals(100_000,service.getInvoiceAmount("INV-1"));
    }

    @Test
    void createInvoiceDuplicateShouldThrow(){
        service.createInvoice("INV-1",100_000);

        DuplicateInvoiceException ex = assertThrows(
                DuplicateInvoiceException.class, () -> service.createInvoice("INV-1",100_000)
        );

        assertTrue(ex.getMessage().contains("INV-1"));
    }

    @Test
    void createInvoiceAmountZeroShouldThrow(){
        InvalidAmountException ex = assertThrows(
                InvalidAmountException.class, () -> service.createInvoice("INV-1",0)
        );
        assertTrue(ex.getMessage().contains("0"));
    }

    @Test
    void getInvoiceNotFoundShouldThrow(){
        InvoiceNotFoundException ex = assertThrows(
                InvoiceNotFoundException.class, () -> service.getInvoiceAmount("INV-5")
        );
    }

    @Test
    void deleteInvoiceSuccess(){
        service.createInvoice("INV-1",100_000);

        service.deleteInvoice("INV-1");

        assertThrows(
                InvoiceNotFoundException.class, () -> service.getInvoiceAmount("INV-1")
        );
    }

    @Test
    void totalAmountShouldSumAllInvoice(){
        service.createInvoice("INV-1",100_000);
        service.createInvoice("INV-2",100_000);

        assertEquals(200_000,service.getTotalAmount());
    }

    @Test
    void createInvoiceAndGet_shouldWorkEvenIfInvoiceIdHasSpaces() {
        service.createInvoice("  INV-TRIM  ", 500_000);
        assertEquals(500_000, service.getInvoiceAmount("INV-TRIM"));
    }
}
