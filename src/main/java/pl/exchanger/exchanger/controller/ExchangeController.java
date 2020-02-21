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
import pl.exchanger.exchanger.exceptions.WrongApiKeyException;
import pl.exchanger.exchanger.model.apiKey.ApiKey;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.CurrencyType;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;
import pl.exchanger.exchanger.model.logs.Log;
import pl.exchanger.exchanger.repository.ApiKeyRepository;
import pl.exchanger.exchanger.repository.LogRepository;

@RequestMapping("/api/")
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

        logRepository.save(new Log(
                HttpMethod.GET,
                "Get API key list",
                HttpStatus.OK));

        return apiKeyRepository.findAll();
    }

    @GetMapping("/list")
    public List<CurrencyType> getAvailableCurrencyList(@RequestParam String apiKey) {


        if (apiKeyRepository.findAll().contains(apiKeyRepository.findByKeyString(apiKey))) {

            logRepository.save(new Log(
                    HttpMethod.GET,
                    "Get available currencyList",
                    apiKeyRepository.findByKeyString(apiKey),
                    HttpStatus.OK));


            return Arrays.asList(CurrencyType.values());

        } else {

            logRepository.save(new Log(
                    HttpMethod.GET,
                    "Get available currencyList",
                    HttpStatus.UNAUTHORIZED));

            throw new WrongApiKeyException(apiKey);
        }
    }

    @PostMapping("/exchange")
    public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request, @RequestParam String apiKey) {

        if (apiKeyRepository.findAll().contains(apiKeyRepository.findByKeyString(apiKey))) {

            logRepository.save(new Log(
                    HttpMethod.POST,
                    "Exchange currency",
                    apiKeyRepository.findByKeyString(apiKey),
                    request.toString(),
                    HttpStatus.OK));

            return exchangeMapper.mapToExchangeDto(request);

        } else {

            logRepository.save( new Log(
                    HttpMethod.GET,
                    "Exchange currency",
                    HttpStatus.UNAUTHORIZED));


            throw new WrongApiKeyException(apiKey);
        }
    }

    @PostMapping("/courselist")
    public List<Currency> printCourses(@RequestBody List<CurrencyType> list, @RequestParam String apiKey) {

        if (apiKeyRepository.findAll().contains(apiKeyRepository.findByKeyString(apiKey))) {

            logRepository.save(new Log(
                    HttpMethod.POST,
                    "Print list of courser of currencies",
                    apiKeyRepository.findByKeyString(apiKey),
                    list.toString(),
                    HttpStatus.OK));

            return listMapper.maptToListDto(list);

        } else {

            logRepository.save(new Log(
                    HttpMethod.GET,
                    "Print list of courser of currencies",
                    HttpStatus.UNAUTHORIZED));

            throw new WrongApiKeyException(apiKey);
        }
    }
}
