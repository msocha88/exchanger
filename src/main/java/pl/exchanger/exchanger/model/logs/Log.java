package pl.exchanger.exchanger.model.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;


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

    private HttpStatus httpStatus;

    private HttpMethod method;

    private Date date;

    private Time time;

    private String quotation;

    private String body;


    public  Log(String error) {
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.body = error;
    }

    public  Log(HttpMethod method, String quotation, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.httpStatus = httpStatus;
    }

    public  Log(HttpMethod method, String quotation, String body, HttpStatus httpStatus) {
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.method = method;
        this.quotation = quotation;
        this.body = body;
        this.httpStatus = httpStatus;
    }

}
