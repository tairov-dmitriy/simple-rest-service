package com.dmitriy.testtasks.restservice;

import org.springframework.context.MessageSource;
import org.springframework.validation.DataBinder;

import java.util.ArrayList;

public class PurchaseRequest {
    private String seller;
    private String customer;
    private ArrayList<Product> products;

    public String getSeller() { return seller; }

    public String getCustomer() { return customer; }

    public ArrayList<Product> getProducts() { return products; }

    public PurchaseResponse check(MessageSource messageSource) {
        PurchaseResponse response = new PurchaseResponse(messageSource);

        PurchaseValidator validator = new PurchaseValidator();
        DataBinder dataBinder = new DataBinder(this);
        dataBinder.setValidator(validator);
        dataBinder.validate();

        response.updateStatus(dataBinder.getBindingResult());

        return response;
    }
}
