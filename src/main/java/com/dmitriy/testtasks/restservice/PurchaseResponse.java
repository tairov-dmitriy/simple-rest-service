package com.dmitriy.testtasks.restservice;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseResponse {
    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "error";

    private String status;
    private ArrayList<String> errors = null;

    public PurchaseResponse() {
        this.status = SUCCESS_STATUS;
    }

    public void updateStatus() {
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

    public ArrayList<String> getErrors() {
        return errors;
    }
}
