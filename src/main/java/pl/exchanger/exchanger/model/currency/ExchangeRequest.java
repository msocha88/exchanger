package pl.exchanger.exchanger.model.currency;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class ExchangeRequest {

    CurrencyType sell;
    CurrencyType buy;
    double amount;

}
