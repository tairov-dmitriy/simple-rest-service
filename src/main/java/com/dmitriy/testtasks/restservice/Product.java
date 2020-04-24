package com.dmitriy.testtasks.restservice;

public class Product {
    private String name;
    private String code;

    public String getName() { return name; }

    public String getCode() { return code; }

    public void check(PurchaseResponse response) {

        // For 13 digits (if it means also letters, use "^\\w{13}$")
        String regExp = "^\\d{13}$";

        if (name == null)
            response.addError("Missed product`s field 'name'");

        if (code == null)
            response.addError("Error in product '" + name + "': missed field 'code'");
        else if (!code.matches(regExp))
            response.addError("Error in product '" + name + "': field 'code' must contains 13 digits");
    }
}
