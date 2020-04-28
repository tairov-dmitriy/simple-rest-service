package com.dmitriy.testtasks.restservice;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PurchaseValidator implements Validator {
    @Override
    public boolean supports(Class c) {
        return PurchaseRequest.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PurchaseRequest purchase = (PurchaseRequest) target;
        ValidationUtils.rejectIfEmpty(errors, "seller", "seller.missed");
        ValidationUtils.rejectIfEmpty(errors, "customer", "customer.missed");
        ValidationUtils.rejectIfEmpty(errors, "products", "products.missed");

        // For 9 digits (if it means also letters, use "^\\w{9}$")
        final String regExp = "^\\d{9}$";

        if (purchase.getSeller() != null && !purchase.getSeller().matches(regExp))
            errors.rejectValue("seller", "seller.illegal");

        if (purchase.getCustomer() != null && !purchase.getCustomer().matches(regExp))
            errors.rejectValue("customer", "customer.illegal");

        if (purchase.getProducts() != null) {
            if (purchase.getProducts().isEmpty())
                errors.rejectValue("products", "products.empty");
            else {
                ProductValidator productValidator = new ProductValidator();
                int i = 0;
                for (Product product : purchase.getProducts()) {
                    try {
                        errors.pushNestedPath("products[" + i + "]");
                        ValidationUtils.invokeValidator(productValidator, product, errors);
                    } finally {
                        errors.popNestedPath();
                    }
                    i++;
                }
            }
        }
    }
}
