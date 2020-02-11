package pl.exchanger.exchanger.dto.mapping;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.dto.ExchangeDto;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;

import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class ExchangeMapper {



    public CurrencyExchanger mapToExchangeDto(ExchangeRequest exchangeRequest) {

        ExchangeDto exchangeDto = new ExchangeDto();
        exchangeRequest.setDateOfQuotation(new Date());
        CurrencyExchanger exchanger = new CurrencyExchanger();

        exchanger.exchange(exchangeRequest);
        exchangeDto.setExchanger(exchanger);

        return exchangeDto.getExchanger();
    }


}
