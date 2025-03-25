package org.example.controllers;

import org.example.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public double getExchangeRate(@RequestParam(defaultValue = "EUR") String currency) {
        return exchangeRateService.get(currency);
    }
}
