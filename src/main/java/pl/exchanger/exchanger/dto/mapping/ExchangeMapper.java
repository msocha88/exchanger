package pl.exchanger.exchanger.dto.mapping;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.dto.ExchangeDto;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;
import pl.exchanger.exchanger.repository.CurrencyRepository;
import pl.exchanger.exchanger.repository.ExchangeRepository;

import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class ExchangeMapper {

    @Autowired
    CurrencyRepository currencyRepository;


    public CurrencyExchanger mapToExchangeDto(ExchangeRequest exchangeRequest) {

        ExchangeDto exchangeDto = new ExchangeDto();
        exchangeRequest.setDateOfQuotation(new Date());
        CurrencyExchanger exchanger = new CurrencyExchanger();

        exchanger.exchange(exchangeRequest);
        exchangeDto.setExchanger(exchanger);

        return exchangeDto.getExchanger();
    }


}
