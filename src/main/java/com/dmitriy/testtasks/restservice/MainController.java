package com.dmitriy.testtasks.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MainController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public PurchaseResponse showStatus() {
        return new PurchaseResponse(null);
    }

    @PostMapping
    public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
        return request.check(messageSource);
    }
}
