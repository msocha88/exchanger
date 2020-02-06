package pl.exchanger.exchanger.model.currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor

public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private CurrencyType currencyType;

    private double course;

    private Date downloadDate;

    @JsonIgnore
    @JoinColumn
    @ManyToOne
    private CurrencyExchanger exchanger;

    public Currency(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void downloadCourse() {

        this.downloadDate = new Date();

//        double course = 0;

        if (currencyType.equals(CurrencyType.PLN)) {
            this.course = 1.0;
        } else {

            try {
                String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyType;

                URL sellLink = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) sellLink.openConnection();
                System.out.println(connection.getResponseCode());

                BufferedReader sellBufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JSONParser parser = new JSONParser();

                JSONObject jsonObject = (JSONObject) parser.parse(sellBufferedReader);
                JSONArray array = (JSONArray) jsonObject.get("rates");
                JSONObject mid = (JSONObject) array.get(0);

                course = ((double) mid.get("mid"));
                course = Math.round(course * 1000) / 1000.0;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}