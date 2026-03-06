package com.feron.invoiceapi.invoice.api.repository;

import com.feron.invoiceapi.invoice.api.dto.InvoiceAmountResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InvoiceRepository {
    private final JdbcTemplate jdbc;

    public InvoiceRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean isInsert(String invoice,long amount){
        int result = jdbc.update("""
        INSERT INTO dbo.invoices(invoice_id, amount) VALUES (?,?)
        """,invoice,amount);

        if (result == 1) {
            return true;
        }

        return false;
    }

    public boolean isExist(String invoice){
        Integer count = jdbc.queryForObject(
          "SELECT COUNT(1) FROM dbo.invoices WHERE invoice_id = ?",
          Integer.class,
                invoice
        );

        return count != null && count > 0;
    }

    public Long findAmount(String invoiceId){
        Long result = jdbc.queryForObject(
                "SELECT amount FROM dbo.invoices WHERE invoice_id = ?",
                Long.class,
                invoiceId
        );

        return result;
    }

    public Long findTotalAmount(){
        return jdbc.queryForObject(
                "SELECT SUM(amount) FROM dbo.invoices",
                Long.class
        );
    }

    public long getTotalByNoInvoices(String invoice){
        Long count = jdbc.queryForObject(
                "SELECT SUM(amount) as totalAmount FROM dbo.invoices WHERE invoice_id = ?",
                Long.class,
                invoice
        );

        return count == null ? 0L : count;
    }

    public int updateAmountByInvoice(String invoice, Long amount){
        int count = jdbc.update(
                "UPDATE dbo.invoices SET amount = ? WHERE invoice_id = ?",
                amount,
                invoice
        );

        return count;
    }

    public int deleteInvoice(String invoice){
        return jdbc.update("DELETE FROM dbo.invoices WHERE invoice_id = ?",invoice);
    }

    public List<InvoiceAmountResponse> findAll() {
        List<Map<String, Object>> rows = jdbc.queryForList("SELECT invoice_id, amount FROM dbo.invoices");

        return rows.stream()
                .map(row -> new InvoiceAmountResponse(
                        (String) row.get("invoice_id"),
                        ((Number) row.get("amount")).longValue()
                ))
                .toList();
    }
}
