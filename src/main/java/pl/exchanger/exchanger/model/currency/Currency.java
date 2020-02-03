package pl.exchanger.exchanger.model.currency;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Entity
@Data
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private CurrencyType currency;

    private double course;


     public void downloadCourse() {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency;
        double course = 0;
        try {
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
        this.course=course;
    }

}
