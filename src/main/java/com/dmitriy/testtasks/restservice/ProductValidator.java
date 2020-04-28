package com.dmitriy.testtasks.restservice;

import org.springframework.validation.*;

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class c) {
        return Product.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmpty(errors, "name", "name.missed");
        ValidationUtils.rejectIfEmpty(errors, "code", "code.missed", new Object[] {product.getName()});

        // For 13 digits (if it means also letters, use "^\\w{13}$")
        if (product.getCode() != null && !product.getCode().matches("^\\d{13}$"))
            errors.rejectValue("code", "code.illegal", new Object[] {product.getName()}, null);
    }
}
