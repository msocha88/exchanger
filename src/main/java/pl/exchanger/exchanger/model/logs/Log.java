package pl.exchanger.exchanger.model.logs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private HttpStatus httpStatus;


    public void insertValues(String method, String quotation, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.httpStatus = httpStatus;
    }

    public void insertValues(String method, String quotation, ApiKey usedApi,HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.usedApi = usedApi;
        this.httpStatus = httpStatus;
    }

    public void insertValues(String method, String quotation, ApiKey usedApi, String body, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.usedApi = usedApi;
        this.httpStatus =httpStatus;
    }

}
