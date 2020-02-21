package pl.exchanger.exchanger.dto.mapping;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.dto.ExchangeDto;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;
import pl.exchanger.exchanger.model.currency.ExchangeRequest;


@Component
@Data
@NoArgsConstructor
public class ExchangeMapper {


    public ExchangeDto mapToExchangeDto(ExchangeRequest exchangeRequest) {

        ExchangeDto exchangeDto = new ExchangeDto();

        CurrencyExchanger exchanger = new CurrencyExchanger();

        exchanger.getSell().setCurrencyType(exchangeRequest.getSell());
        exchanger.getSell().downloadCourse();

        exchanger.getBuy().setCurrencyType(exchangeRequest.getBuy());
        exchanger.getBuy().downloadCourse();


        exchanger.setAmount(exchangeRequest.getAmount());

        exchanger.setExchangeRatio(Math.round(
                (exchanger.getSell().getCourse() / exchanger.getBuy().getCourse()) * 1000) / 1000.0);


        exchanger.setPayment(Math.round((exchanger.getAmount() * exchanger.getExchangeRatio()) * 100) / 100.0);

        exchangeDto.setExchanger(exchanger);

        return exchangeDto;
    }


}

