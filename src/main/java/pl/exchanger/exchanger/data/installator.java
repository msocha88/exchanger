package pl.exchanger.exchanger.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.model.apiKey.ApiKey;
import pl.exchanger.exchanger.repository.ApiKeyRepository;

@Component
public class installator implements CommandLineRunner {

    @Autowired
    ApiKeyRepository apiKeyRepository;

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 15; i++) {

            ApiKey apiKey = new ApiKey();
            apiKey.setLength(16);
            apiKey.generateKey();
            apiKeyRepository.save(apiKey);
      }
    }
}