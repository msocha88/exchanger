package pl.exchanger.exchanger.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.model.currency.apiKey.ApiKey;
import pl.exchanger.exchanger.repository.ApiKeyRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class instalator implements CommandLineRunner {


    @Autowired
    ApiKeyRepository apiKeyRepository;


    @Override
    public void run(String... args) throws Exception {




        for (int i = 0; i < 15; i++) {

            ApiKey apiKey = new ApiKey();
            apiKey.setLength(10);
            apiKey.generateKey();
            apiKeyRepository.save(apiKey);
      }


        System.out.println("jede");
    }
}
