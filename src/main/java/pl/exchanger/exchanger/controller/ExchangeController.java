package pl.exchanger.exchanger.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.exchanger.exchanger.dto.mapping.ExchangeMapper;
import pl.exchanger.exchanger.dto.mapping.ListMapper;
import pl.exchanger.exchanger.model.currency.*;
import pl.exchanger.exchanger.repository.CurrencyListRepository;
import pl.exchanger.exchanger.repository.ExchangeRepository;

import java.util.*;

@RequestMapping("/api/")
@RestController
public class ExchangeController {

    @Autowired
    ListMapper listMapper;

    @Autowired
    CurrencyListRepository currencyListRepository;

    @Autowired
    ExchangeMapper exchangeMapper;

    @Autowired
    ExchangeRepository exchangeRepository;

    @GetMapping("list")
    public List<CurrencyType> getList() {
        return Arrays.asList(CurrencyType.values());
    }


    @PostMapping("exchange")
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request) {

        CurrencyExchanger exchanger = exchangeMapper.mapToExchangeDto(request);

        exchangeRepository.save(exchanger);

        return exchanger;

    }

    @PostMapping("courselist")
    public CurrencyList getCourseList(@RequestBody List<CurrencyType> list) {

        CurrencyList outputList = listMapper.maptToListDto(list);

        currencyListRepository.save(outputList);

        return outputList;
    }
}
