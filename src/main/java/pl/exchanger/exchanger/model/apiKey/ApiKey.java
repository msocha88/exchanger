package pl.exchanger.exchanger.model.apiKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.exchanger.exchanger.model.logs.Log;
import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @JsonIgnore
    private int length;

    private String keyString;

    @Transient
    @JsonIgnore
    private final String allChars
            = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @OneToMany(mappedBy = "usedApi")
    @JsonIgnore
    private List<Log> logList;

    public void generateKey() {

        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            Random random = new Random();
            int character = random.nextInt(+allChars.length());
            key.append(allChars.charAt(character));
        }

        this.keyString = key.toString();
    }
}
