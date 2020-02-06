package pl.exchanger.exchanger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.exchanger.exchanger.model.currency.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency,Long> {

}
