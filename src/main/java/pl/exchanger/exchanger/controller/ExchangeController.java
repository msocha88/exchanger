package pl.exchanger.exchanger.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.exchanger.exchanger.dto.mapping.ExchangeMapper;
import pl.exchanger.exchanger.dto.mapping.ListMapper;
import pl.exchanger.exchanger.model.apiKey.ApiKey;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.CurrencyType;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;
import pl.exchanger.exchanger.model.logs.Log;
import pl.exchanger.exchanger.repository.ApiKeyRepository;
import pl.exchanger.exchanger.repository.LogRepository;

@RequestMapping({"/api/"})
@RestController
public class ExchangeController {
    @Autowired
    ListMapper listMapper;
    @Autowired
    ApiKeyRepository apiKeyRepository;
    @Autowired
    ExchangeMapper exchangeMapper;
    @Autowired
    ApiKeyRepository keyListRepository;
    @Autowired
    LogRepository logRepository;

    public ExchangeController() {
    }

    @GetMapping({"apiKeys"})
    public List<ApiKey> getApiKeyList() {
        Iterable<ApiKey> apiKeyAll = this.keyListRepository.findAll();
        List<ApiKey> apiKeys = new ArrayList();
        Iterator var3 = apiKeyAll.iterator();

        while(var3.hasNext()) {
            ApiKey apiKey = (ApiKey)var3.next();
            apiKeys.add(apiKey);
        }

        Log log = new Log();
        log.setDate(new Date(System.currentTimeMillis()));
        log.setMethod("GET");
        log.setQuotation("Get API Keys List");
        this.logRepository.save(log);
        return apiKeys;
    }

    @GetMapping({"list"})
    public List<CurrencyType> getAvailableCurrencyList(@RequestParam String apiKey) {
        Iterable<ApiKey> apiKeyAll = this.keyListRepository.findAll();
        Iterator var3 = apiKeyAll.iterator();

        ApiKey key;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            key = (ApiKey)var3.next();
        } while(!key.getKeyString().equals(apiKey));

        Log log = new Log();
        log.setDate(new Date(System.currentTimeMillis()));
        log.setMethod("GET");
        log.setQuotation("Get available currencyList");
        log.setUsedApi(this.apiKeyRepository.findByKeyString(apiKey));
        this.logRepository.save(log);
        return Arrays.asList(CurrencyType.values());
    }

    @PostMapping({"exchange"})
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request, @RequestParam String apiKey) {
        Iterable<ApiKey> apiKeyAll = this.keyListRepository.findAll();
        Iterator var4 = apiKeyAll.iterator();

        ApiKey key;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            key = (ApiKey)var4.next();
        } while(!key.getKeyString().equals(apiKey));

        CurrencyExchanger exchanger = this.exchangeMapper.mapToExchangeDto(request);
        Log log = new Log();
        log.setDate(new Date(System.currentTimeMillis()));
        log.setMethod("POST");
        log.setQuotation("Exchange currency");
        log.setUsedApi(this.apiKeyRepository.findByKeyString(apiKey));
        log.setBody(request.toString());
        this.logRepository.save(log);
        return exchanger;
    }

    @PostMapping({"courselist"})
    public List<Currency> printCourses(@RequestBody List<CurrencyType> list, @RequestParam String apiKey) {
        Iterable<ApiKey> apiKeyAll = this.keyListRepository.findAll();
        Iterator var4 = apiKeyAll.iterator();

        ApiKey key;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            key = (ApiKey)var4.next();
        } while(!key.getKeyString().equals(apiKey));

        List<Currency> outputList = this.listMapper.maptToListDto(list);
        Log log = new Log();
        log.setDate(new Date(System.currentTimeMillis()));
        log.setMethod("POST");
        log.setQuotation("Exchange currency");
        log.setUsedApi(this.apiKeyRepository.findByKeyString(apiKey));
        log.setBody(list.toString());
        this.logRepository.save(log);
        return outputList;
    }
}
