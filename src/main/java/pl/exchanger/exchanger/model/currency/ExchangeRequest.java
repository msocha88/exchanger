package pl.exchanger.exchanger.model.currency;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.model.currency.CurrencyType;

import javax.persistence.Entity;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
public class ExchangeRequest {

    CurrencyType sell;
    CurrencyType buy;
    double amount;
    private Date dateOfQuotation;

}
