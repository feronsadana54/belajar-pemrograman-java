package com.feron.invoiceapi.invoice.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createThenGetShouldReturn200() throws Exception {
        int random = (int) (Math.random() * 100);
        String id = "INV-"+ random;

        CreateInvoiceRequest req =  new CreateInvoiceRequest();
        req.setInvoiceId(id);
        req.setAmount(500_000);

        mvc.perform(post("/invoices").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req))).andExpect(status().isCreated());

        mvc.perform(get("/invoices/"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value(id))
                .andExpect(jsonPath("$.amount").value(500_000));
    }

    @Test
    void createDuplicateShouldReturn409() throws Exception {
        int random = (int) (Math.random() * 100);
        String id = "INV-"+ random;

        CreateInvoiceRequest req =  new CreateInvoiceRequest();
        req.setInvoiceId(id);
        req.setAmount(500_000);

        mvc.perform(post("/invoices").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(post("/invoices").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("DUPLICATE_INVOICE"));
    }

    @Test
    void getNotFoundShouldReturn404() throws Exception {
        int random = (int) (Math.random() * 100);
        String id = "INV-"+ random;

        mvc.perform(get("/invoices/"+id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteThenGetShouldReturn404() throws Exception {
        int random = (int) (Math.random() * 100);
        String id = "INV-"+ random;

        mvc.perform(delete("/invoices/"+id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createInvalidShouldReturn400ValidationError() throws Exception {
        String json = """
                {"invoiceId":"1","amount":0}
                """;

        mvc.perform(post("/invoices").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
        ;
    }

    @Test
    void getTotalAmountShouldReturn200() throws Exception {
        CreateInvoiceRequest req1 = new CreateInvoiceRequest();
        CreateInvoiceRequest req2 = new CreateInvoiceRequest();
        req1.setInvoiceId("INV-A");
        req2.setInvoiceId("INV-B");
        req1.setAmount(100_000);
        req2.setAmount(200_000);

        mvc.perform(post("/invoices").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req1)))
                .andExpect(status().isCreated());
        mvc.perform(post("/invoices").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req2)))
                .andExpect(status().isCreated());
        mvc.perform(get("/invoices/total").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(300000));
    }
}
