package com.feron.invoiceapi.invoice.api;

import com.feron.invoiceapi.invoice.errors.DuplicateInvoiceException;
import com.feron.invoiceapi.invoice.errors.InvalidAmountException;
import com.feron.invoiceapi.invoice.errors.InvoiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    public static class ErrorResponse{
        public String error;
        public String message;

        public ErrorResponse(String error, String message){
            this.error = error;
            this.message = message;
        }
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFound(InvoiceNotFoundException ex){
        return new ErrorResponse("INVOICE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidAmount(InvalidAmountException ex){
        return new ErrorResponse("INVALID_AMOUNT", ex.getMessage());
    }

    @ExceptionHandler(DuplicateInvoiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleDuplicateInvoice(DuplicateInvoiceException ex){
        return new ErrorResponse("DUPLICATE_INVOICE", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return new ErrorResponse("BAD_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntimeException(RuntimeException ex){
        return new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleValidation(MethodArgumentNotValidException ex){
        Map<String, List<String>> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            String field = err.getField();
            String message = err.getDefaultMessage();

            fieldErrors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
        });
        return new ValidationErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed",
                fieldErrors
        );
    }
}
