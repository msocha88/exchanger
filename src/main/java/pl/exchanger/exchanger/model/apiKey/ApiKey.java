package pl.exchanger.exchanger.model.apiKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private int length;

    private String keyString;

    public ApiKey(String keyString) {
        this.keyString = keyString;
    }

    @JsonIgnore
    private final String allChars
            = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public void generateKey() {

        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            Random random = new Random();
            int character = random.nextInt(+allChars.length());
            key.append(allChars.charAt(character));
        }

        keyString = key.toString();
    }

}
