package pl.exchanger.exchanger.repository;

import org.springframework.data.repository.CrudRepository;
import pl.exchanger.exchanger.model.currency.apiKey.ApiKey;

public interface ApiKeyRepository extends CrudRepository<ApiKey, Long> {
}
