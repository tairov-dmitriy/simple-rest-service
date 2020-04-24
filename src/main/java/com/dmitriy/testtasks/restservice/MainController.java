package com.dmitriy.testtasks.restservice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MainController {

    @GetMapping
    public PurchaseResponse showStatus() {
        return new PurchaseResponse();
    }

    @PostMapping
    public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
        return request.check();
    }
}
