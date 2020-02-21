package pl.exchanger.exchanger.model.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import pl.exchanger.exchanger.model.apiKey.ApiKey;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private Date date;

    private Time time;

    private String quotation;

    private String body;

    @ManyToOne
    private ApiKey usedApi;

    private HttpStatus httpStatus;


    public void insertValues(String method, String quotation, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.httpStatus = httpStatus;
    }

    public void insertValues(String method, String quotation, ApiKey usedApi, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.usedApi = usedApi;
        this.httpStatus = httpStatus;
    }

    public void insertValues(String method, String quotation, ApiKey usedApi, String body, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.usedApi = usedApi;
        this.httpStatus = httpStatus;
    }

}
