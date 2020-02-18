package pl.exchanger.exchanger.dto;

import lombok.Data;
import pl.exchanger.exchanger.model.currency.Currency;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDto {

    private List<Currency> currencyList = new ArrayList<>();

}
