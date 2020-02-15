package pl.exchanger.exchanger.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.exchanger.exchanger.dto.mapping.ExchangeMapper;
import pl.exchanger.exchanger.dto.mapping.ListMapper;
import pl.exchanger.exchanger.model.currency.*;
import pl.exchanger.exchanger.model.apiKey.ApiKey;
import pl.exchanger.exchanger.repository.CurrencyListRepository;
import pl.exchanger.exchanger.repository.ExchangeRepository;
import pl.exchanger.exchanger.repository.ApiKeyRepository;

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

    @Autowired
    ApiKeyRepository keyListRepository;

    @GetMapping("apiKeys")
    public List<ApiKey> getApiList() {

        Iterable<ApiKey> apiKeyAll = keyListRepository.findAll();

        List<ApiKey> apiKeys = new ArrayList<>();

        for (ApiKey apiKey : apiKeyAll) {
            apiKeys.add(apiKey);
        }

        return apiKeys;
    }

    @GetMapping("list")

    public List<CurrencyType> getAvailableCurrencyList(@RequestParam String apiKey) {

        Iterable<ApiKey> apiKeyAll = keyListRepository.findAll();

        for (ApiKey key : apiKeyAll) {

            if (key.getKeyString().equals(apiKey)) {

                return Arrays.asList(CurrencyType.values());
            }
        }

        return null;
    }

    @PostMapping("exchange")
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request, @RequestParam String apiKey) {

        Iterable<ApiKey> apiKeyAll = keyListRepository.findAll();

        for (ApiKey key : apiKeyAll) {
            if (key.getKeyString().equals(apiKey)) {

                CurrencyExchanger exchanger = exchangeMapper.mapToExchangeDto(request);

                exchangeRepository.save(exchanger);

                return exchanger;
            }
        }
        return null;
    }

    @PostMapping("courselist")
    public CurrencyList printCourses(@RequestBody List<CurrencyType> list, @RequestParam String apiKey) {

        Iterable<ApiKey> apiKeyAll = keyListRepository.findAll();

        for (ApiKey key : apiKeyAll) {

            if (key.getKeyString().equals(apiKey)) {

                CurrencyList outputList = listMapper.maptToListDto(list);
                currencyListRepository.save(outputList);

                return outputList;
            }
        }
        return null;
    }
}
