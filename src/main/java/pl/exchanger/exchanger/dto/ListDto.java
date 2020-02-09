package pl.exchanger.exchanger.dto;

import lombok.Data;
import pl.exchanger.exchanger.model.currency.CurrencyList;

@Data
public class ListDto {

    private CurrencyList currencyList = new CurrencyList();

}
