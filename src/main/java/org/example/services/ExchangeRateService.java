package org.example.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateService {
    private static final String API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=%s&date=%s&json";

    public static class ExchangeRate {
        private int r030;
        private String txt;
        private double rate;
        private String cc;
        private String exchangedate;

        public double getRate() {
            return rate;
        }
    }

    public double get(String currency) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = today.format(formatter);

        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(API_URL, currency, dateString);

        ResponseEntity<ExchangeRate[]> response = restTemplate.getForEntity(url, ExchangeRate[].class);
        ExchangeRate[] rates = response.getBody();

        if (rates != null && rates.length > 0) {
            return rates[0].getRate();
        } else {
            throw new RuntimeException("No exchange rate data found for currency: " + currency);
        }
    }
}
