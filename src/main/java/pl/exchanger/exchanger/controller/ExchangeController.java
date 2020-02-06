package pl.exchanger.exchanger.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.exchanger.exchanger.dto.mapping.ExchangeMapper;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.CurrencyType;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;
import pl.exchanger.exchanger.repository.ExchangeRepository;

import java.util.*;

@RequestMapping("/api/")
@RestController
public class ExchangeController {

    @Autowired
    ExchangeMapper exchangeMaper;
    @Autowired
    ExchangeRepository exchangeRepository;

    @GetMapping("list")
    public List<CurrencyType> getList() {
        return Arrays.asList(CurrencyType.values());
    }


    @PostMapping("exchange")
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request) {

        CurrencyExchanger exchanger = exchangeMaper.mapToExchangeDto(request);

        exchangeRepository.save(exchanger);

        return exchanger;

    }

    @PostMapping("courselist")
    public Map<CurrencyType, Double> getCourseList(@RequestBody CurrencyType[] list) {

        Map<CurrencyType, Double> coursesMap = new LinkedHashMap<>();

        for (CurrencyType currencyType : list) {

            if (!currencyType.equals(CurrencyType.PLN)) {
                Currency currency = new Currency();
                currency.setCurrencyType(currencyType);
                currency.downloadCourse();
                coursesMap.put(currency.getCurrencyType(), currency.getCourse());
            } else {
                coursesMap.put(currencyType, 1.00);
            }
        }
        return coursesMap;
    }
}
