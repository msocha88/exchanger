package pl.exchanger.exchanger.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.exchanger.exchanger.dto.mapping.ExchangeMapper;
import pl.exchanger.exchanger.dto.mapping.ListMapper;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.CurrencyType;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;
import pl.exchanger.exchanger.model.logs.Log;
import pl.exchanger.exchanger.repository.LogRepository;

@RequestMapping("/api/")
@RestController
public class ExchangeController {

    @Autowired
    ListMapper listMapper;

    @Autowired
    ExchangeMapper exchangeMapper;

    @Autowired
    LogRepository logRepository;


    @GetMapping("/list")
    public List<CurrencyType> getAvailableCurrencyList() {

        logRepository.save(new Log(
                HttpMethod.GET,
                "Get available currencyList",
                HttpStatus.OK));

        return Arrays.asList(CurrencyType.values());

    }


    @PostMapping("/exchange")
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request) {

        logRepository.save(new Log(
                HttpMethod.POST,
                "Exchange currency",
                HttpStatus.OK));

        return exchangeMapper.mapToExchangeDto(request).getExchanger();

    }

    @PostMapping("/courselist")
    public List<Currency> printCourses(@RequestBody List<CurrencyType> list, @RequestParam String apiKey) {

        logRepository.save(new Log(
                HttpMethod.POST,
                "Print list of courser of currencies",
                list.toString(),
                HttpStatus.OK));

        return listMapper.maptToListDto(list).getCurrencyList();
    }
}
