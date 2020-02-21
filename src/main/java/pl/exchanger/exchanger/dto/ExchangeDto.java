package pl.exchanger.exchanger.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeDto {


    private ExchangeRequest exchangeRequest;
    private CurrencyExchanger exchanger;


}
