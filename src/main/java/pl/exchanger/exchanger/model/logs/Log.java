package pl.exchanger.exchanger.model.logs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.model.apiKey.ApiKey;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private Date date;

    private String quotation;

    private String body;

    @ManyToOne
    private ApiKey usedApi;


}
