package pl.exchanger.exchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;

@Repository
public interface ExchangeRepository extends CrudRepository<CurrencyExchanger,Long> {
}
