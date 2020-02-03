package pl.exchanger.exchanger.model.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "exchanges")
public class CurrencyExchanger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Currency sell = new Currency();
    Currency buy = new Currency();

    double amount;
    double exchangeRatio;
    double payment;

    public void exchange() {

        if (!sell.getCurrency().equals(CurrencyType.PLN)) {
            sell. downloadCourse();
        } else {
            sell.setCourse(1.0);
        }

        if (!buy.getCurrency().equals(CurrencyType.PLN)) {
            buy.downloadCourse();

        } else {
            buy.setCourse(1.0);
        }

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

    public void setSell(Currency sell) {
        this.sell = sell;
    }

    public void setSellCurrency(CurrencyType sell) {
        this.sell.setCurrency(sell);
    }

    public void setBuy(Currency buy) {
        this.buy = buy;
    }

    public void setBuyCurrency(CurrencyType buy) {
        this.buy.setCurrency(buy);
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
