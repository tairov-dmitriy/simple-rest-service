package com.dmitriy.testtasks.restservice;

import java.util.ArrayList;

public class PurchaseRequest {
    private String seller;
    private String customer;
    private ArrayList<Product> products;

    public String getSeller() { return seller; }

    public String getCustomer() { return customer; }

    public ArrayList<Product> getProducts() { return products; }

    public PurchaseResponse check() {
        PurchaseResponse response = new PurchaseResponse();

        // For 9 digits (if it means also letters, use "^\\w{9}$")
        String regExp = "^\\d{9}$";

        if (seller == null)
            response.addError("Missed field 'seller'");
        else if (!seller.matches(regExp))
            response.addError("Field 'seller' must contains 9 digits");

        if (customer == null)
            response.addError("Missed field 'customer'");
        else if (!customer.matches(regExp))
            response.addError("Field 'customer' must contains 9 digits");

        if (products == null)
            response.addError("Missed field 'products'");
        else if (products.isEmpty())
            response.addError("Empty products list");
        else
            for (Product product: products)
                product.check(response);

        response.updateStatus();

        return response;
    }
}
