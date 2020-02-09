package pl.exchanger.exchanger.model.currency;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    private Date exchangeDate;

    @OneToMany(mappedBy = "exchanger", cascade = CascadeType.ALL)
    private List<Currency> currencies = new ArrayList<>();

    public void exchange(ExchangeRequest request) {

        exchangeDate = new Date();

        Currency sell = new Currency();
        sell.setCurrencyType(request.getSell());
        sell.setExchanger(this);
        sell.downloadCourse();

        Currency buy = new Currency();
        buy.setCurrencyType(request.getBuy());
        buy.setExchanger(this);
        buy.downloadCourse();

        currencies.add(sell);
        currencies.add(buy);

        amount = request.getAmount();
        exchangeRatio = Math.round((sell.getCourse() / buy.getCourse()) * 1000) / 1000.0;
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
