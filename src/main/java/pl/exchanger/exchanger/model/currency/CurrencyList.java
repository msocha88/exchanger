package pl.exchanger.exchanger.model.currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CurrencyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonIgnore
    private Long id;

    private Date dateOfQuotation = new Date();

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    private List<Currency> currencyList = new ArrayList<>();

    public void addCurrency(Currency currency) {
        currency.setList(this);
        currencyList.add(currency);
    }
}
