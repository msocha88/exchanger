package pl.exchanger.exchanger.model.currency;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class CurrencyExchanger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double amount;
    double exchangeRatio;
    double payment;

    @OneToMany(mappedBy = "exchanger", cascade = CascadeType.ALL)
    private List<Currency> currencies = new ArrayList<>();

    public void exchange(ExchangeRequest request) {

        Currency sell = new Currency();
        sell.setCurrencyType(request.getSell());
        sell.setExchanger(this);

        Currency buy = new Currency();
        buy.setCurrencyType(request.getBuy());
        buy.setExchanger(this);

        currencies.add(sell);
        currencies.add(buy);

        for (Currency currency : currencies) {

            if (currency.getCurrencyType().equals(CurrencyType.PLN)) {
                currency.setCourse(1.0);
            } else {
                currency.downloadCourse();
            }
        }

        amount = request.getAmount();
        exchangeRatio = Math.round((currencies.get(0).getCourse() / currencies.get(1).getCourse()) * 1000) / 1000.0;
        payment = Math.round((amount * exchangeRatio) * 100) / 100.0;
    }

    public double getAmount() {
        return amount;
    }

    public double getExchangeRatio() {
        return exchangeRatio;
    }

    public double getPayment() {
        return payment;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setExchangeRatio(double exchangeRatio) {
        this.exchangeRatio = exchangeRatio;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
