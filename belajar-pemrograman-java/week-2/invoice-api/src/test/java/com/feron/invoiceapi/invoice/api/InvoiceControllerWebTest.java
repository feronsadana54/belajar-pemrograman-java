package com.feron.invoiceapi.invoice.api;

import com.feron.invoiceapi.invoice.InvoiceService;
import com.feron.invoiceapi.invoice.errors.DuplicateInvoiceException;
import com.feron.invoiceapi.invoice.errors.InvoiceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InvoiceController.class)
public class InvoiceControllerWebTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InvoiceService service;

    @Test
    void postInvoicesSuccessShouldReturn201() throws Exception{
        CreateInvoiceRequest req = new CreateInvoiceRequest();
        req.setInvoiceId(" INV-TEST");
        req.setAmount(500_000);

        doNothing().when(service).createInvoice("INV-TEST", 500_000);

        mvc.perform(
                post("/invoices").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated());

        verify(service).createInvoice("INV-TEST", 500_000);
    }

    @Test
    void postInvoicesInvalidShouldReturn400WithFields()throws Exception{
        String json = """
                {"invoiceId":"1","amount":0}
                """;
        mvc.perform(
                post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fields.invoiceId").isArray())
                .andExpect(jsonPath("$.fields.amount").isArray());
    }

    @Test
    void getInvoiceNotFoundShouldReturn404() throws Exception{
        when(service.getInvoiceAmount("INV-TESTX"))
                .thenThrow(new InvoiceNotFoundException("INV-TESTX"));

        mvc.perform(get("/invoices/INV-TESTX"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("INVOICE_NOT_FOUND"));
    }

    @Test
    void getInvoiceSuccessShouldReturn200AndBody() throws Exception{
        when(service.getInvoiceAmount("INV-TESTX")).thenReturn(100_000L);

        mvc.perform(get("/invoices/INV-TESTX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value("INV-TESTX"))
                .andExpect(jsonPath("$.amount").value(100_000));
    }

    @Test
    void deleteInvoiceSuccessShouldReturn204() throws Exception{
        doNothing().when(service).deleteInvoice("INV-1");

        mvc.perform(delete("/invoices/INV-1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void postInvoicesDuplicateShouldReturn409() throws Exception{
        CreateInvoiceRequest req = new CreateInvoiceRequest();
        req.setInvoiceId("INV-1");
        req.setAmount(100_000L);
        doThrow(new DuplicateInvoiceException("INV-1")).when(service).createInvoice("INV-1", 100_000);
        mvc.perform(post("/invoices").contentType(
                MediaType.APPLICATION_JSON
        ).content(objectMapper.writeValueAsString(req))).andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("DUPLICATE_INVOICE"));
    }
}
