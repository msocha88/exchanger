package pl.exchanger.exchanger.dto;

import lombok.Data;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;

@Data
public class ExchangeDto {


    private ExchangeRequest exchangeRequest;
    private CurrencyExchanger exchanger;


}
