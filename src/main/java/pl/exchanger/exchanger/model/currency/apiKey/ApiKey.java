package pl.exchanger.exchanger.model.currency.apiKey;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int length =(int) Math.random();

    private String keyString;


    public void generateKey() {
        final String ANString
                = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            Random random = new Random();
            int character = random.nextInt(+ANString.length());
            key.append(ANString.charAt(character));
        }

        keyString = key.toString();
    }

}
