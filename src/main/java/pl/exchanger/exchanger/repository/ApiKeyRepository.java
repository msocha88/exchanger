package pl.exchanger.exchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pl.exchanger.exchanger.model.apiKey.ApiKey;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    ApiKey findByKeyString(String keyString);
}
