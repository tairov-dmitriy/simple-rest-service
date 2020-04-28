package com.dmitriy.testtasks.restservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseResponse {
    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "error";

    private MessageSource messageSource;

    private String status;
    private List<String> errors = null;

    public PurchaseResponse(MessageSource messageSource) {
        this.status = SUCCESS_STATUS;
        this.messageSource = messageSource;
    }

    public void updateStatus(BindingResult result) {
        result.getAllErrors().forEach(err ->
            addError(messageSource.getMessage(err.getCode(), err.getArguments(), Locale.getDefault()))
        );

        if (errors == null || errors.isEmpty())
            this.status = SUCCESS_STATUS;
        else
            this.status = ERROR_STATUS;
    }

    public String getStatus() {
        return status;
    }

    public void addError(String error) {
        if (this.errors == null)
            this.errors = new ArrayList<>();

        this.errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }
}
