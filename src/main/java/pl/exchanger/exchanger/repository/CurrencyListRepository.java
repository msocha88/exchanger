package pl.exchanger.exchanger.repository;

import org.springframework.data.repository.CrudRepository;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyList;

import java.util.List;

public interface CurrencyListRepository extends CrudRepository<CurrencyList, Long> {
}
