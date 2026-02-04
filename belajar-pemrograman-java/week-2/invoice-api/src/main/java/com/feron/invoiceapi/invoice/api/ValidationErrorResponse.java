package com.feron.invoiceapi.invoice.api;

import java.util.List;
import java.util.Map;

public class ValidationErrorResponse {

    public String error;
    public String message;
    public Map<String, List<String>> fields;

    public ValidationErrorResponse(String error, String message, Map<String, List<String>> fields) {
        this.error = error;
        this.message = message;
        this.fields = fields;
    }
}
