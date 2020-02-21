package pl.exchanger.exchanger.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.exchanger.exchanger.dto.mapping.ExchangeMapper;
import pl.exchanger.exchanger.dto.mapping.ListMapper;
import pl.exchanger.exchanger.exceptions.WrongApiKeyException;
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
    LogRepository logRepository;


    @GetMapping("/apiKeys")
    public List<ApiKey> getApiKeyList() {

        Log log = new Log();

        log.insertValues(
                "GET",
                "Get API key list",
                HttpStatus.OK);
        this.logRepository.save(log);

        return apiKeyRepository.findAll();
    }

    @GetMapping("/list")
    public List<CurrencyType> getAvailableCurrencyList(@RequestParam String apiKey) {


        if (apiKeyRepository.findAll().contains(apiKeyRepository.findByKeyString(apiKey))) {

            Log log = new Log();

            log.insertValues(
                    "GET",
                    "Get available currencyList",
                    apiKeyRepository.findByKeyString(apiKey),
                    HttpStatus.OK);

            this.logRepository.save(log);

            return Arrays.asList(CurrencyType.values());

        } else {

            Log log = new Log();
            log.insertValues(
                    "GET",
                    "Get available currencyList",
                    HttpStatus.UNAUTHORIZED);

            this.logRepository.save(log);

            throw new WrongApiKeyException(apiKey);
        }
    }

    @PostMapping("/exchange")
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request, @RequestParam String apiKey) {

        if (apiKeyRepository.findAll().contains(apiKeyRepository.findByKeyString(apiKey))) {

            Log log = new Log();

            log.insertValues(

                    "POST",
                    "Exchange currency",
                    apiKeyRepository.findByKeyString(apiKey),
                    request.toString(),
                    HttpStatus.OK);

            logRepository.save(log);

            return exchangeMapper.mapToExchangeDto(request);

        } else {

            Log log = new Log();
            log.insertValues(
                    "GET",
                    "Exchange currency",
                    HttpStatus.UNAUTHORIZED);

            this.logRepository.save(log);

            throw new WrongApiKeyException(apiKey);
        }
    }

    @PostMapping("/courselist")
    public List<Currency> printCourses(@RequestBody List<CurrencyType> list, @RequestParam String apiKey) {

        if (apiKeyRepository.findAll().contains(apiKeyRepository.findByKeyString(apiKey))) {

            Log log = new Log();

            log.insertValues(
                    "POST",
                    "Print list of courser of currencies",
                    apiKeyRepository.findByKeyString(apiKey),
                    list.toString(),
                    HttpStatus.OK);
            logRepository.save(log);

            return listMapper.maptToListDto(list);

        } else {

            Log log = new Log();
            log.insertValues(
                    "GET",
                    "Print list of courser of currencies",
                    HttpStatus.UNAUTHORIZED);

            this.logRepository.save(log);

            throw new WrongApiKeyException(apiKey);
        }
    }
}
