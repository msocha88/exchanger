package pl.exchanger.exchanger.model.currency;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyExchanger {

    double amount;

    double exchangeRatio;

    double payment;

    private Currency sell = new Currency();

    private Currency buy = new Currency();

}
