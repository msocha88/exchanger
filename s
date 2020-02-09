[33mcommit e128a1e6a329ad3254ba38437f147362d194adc9[m[33m ([m[1;36mHEAD -> [m[1;32mpoprawka1[m[33m)[m
Author: micsoc <michal.socha.88@gmail.com>
Date:   Thu Feb 6 23:39:17 2020 +0100

    Working save to db exchanges

[1mdiff --git a/src/main/java/pl/exchanger/exchanger/controller/ExchangeController.java b/src/main/java/pl/exchanger/exchanger/controller/ExchangeController.java[m
[1mindex 33c54f0..bea84df 100644[m
[1m--- a/src/main/java/pl/exchanger/exchanger/controller/ExchangeController.java[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/controller/ExchangeController.java[m
[36m@@ -1,21 +1,25 @@[m
 package pl.exchanger.exchanger.controller;[m
 [m
 [m
[32m+[m[32mimport org.springframework.beans.factory.annotation.Autowired;[m
 import org.springframework.web.bind.annotation.*;[m
[31m-import pl.exchanger.exchanger.dto.ExchangeDto;[m
[32m+[m[32mimport pl.exchanger.exchanger.dto.mapping.ExchangeMapper;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.Currency;[m
 import pl.exchanger.exchanger.model.currency.CurrencyExchanger;[m
 import pl.exchanger.exchanger.model.currency.CurrencyType;[m
 import pl.exchanger.exchanger.model.currency.ExchangeRequest;[m
[32m+[m[32mimport pl.exchanger.exchanger.repository.ExchangeRepository;[m
 [m
[31m-import java.util.Arrays;[m
[31m-import java.util.LinkedHashMap;[m
[31m-import java.util.List;[m
[31m-import java.util.Map;[m
[32m+[m[32mimport java.util.*;[m
 [m
 @RequestMapping("/api/")[m
 @RestController[m
 public class ExchangeController {[m
 [m
[32m+[m[32m    @Autowired[m
[32m+[m[32m    ExchangeMapper exchangeMaper;[m
[32m+[m[32m    @Autowired[m
[32m+[m[32m    ExchangeRepository exchangeRepository;[m
 [m
     @GetMapping("list")[m
     public List<CurrencyType> getList() {[m
[36m@@ -26,32 +30,28 @@[m [mpublic class ExchangeController {[m
     @PostMapping("exchange")[m
     public CurrencyExchanger exchangeCurrency(@RequestBody ExchangeRequest request) {[m
 [m
[31m-        CurrencyExchanger exchanger = new CurrencyExchanger();[m
[32m+[m[32m        CurrencyExchanger exchanger = exchangeMaper.mapToExchangeDto(request);[m
 [m
[31m-        exchanger.setAmount(request.getAmount());[m
[31m-        exchanger.setBuyCurrency(request.getBuy());[m
[31m-        exchanger.setSellCurrency(request.getSell());[m
[31m-        exchanger.exchange();[m
[32m+[m[32m        exchangeRepository.save(exchanger);[m
 [m
         return exchanger;[m
[32m+[m
     }[m
 [m
     @PostMapping("courselist")[m
     public Map<CurrencyType, Double> getCourseList(@RequestBody CurrencyType[] list) {[m
 [m
         Map<CurrencyType, Double> coursesMap = new LinkedHashMap<>();[m
[31m-        CurrencyExchanger exchanger = new CurrencyExchanger();[m
[31m-[m
[31m-        for (CurrencyType currency : list) {[m
[31m-            if (!currency.equals(CurrencyType.PLN)) {[m
[31m-[m
[31m-                ExchangeDto exchangeDto = new ExchangeDto();[m
[31m-                exchangeDto.map[m
[31m-                //MapExchangeDPO HERE[m
 [m
[32m+[m[32m        for (CurrencyType currencyType : list) {[m
 [m
[32m+[m[32m            if (!currencyType.equals(CurrencyType.PLN)) {[m
[32m+[m[32m                Currency currency = new Currency();[m
[32m+[m[32m                currency.setCurrencyType(currencyType);[m
[32m+[m[32m                currency.downloadCourse();[m
[32m+[m[32m                coursesMap.put(currency.getCurrencyType(), currency.getCourse());[m
             } else {[m
[31m-                coursesMap.put(currency, 1.00);[m
[32m+[m[32m                coursesMap.put(currencyType, 1.00);[m
             }[m
         }[m
         return coursesMap;[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/controller/FrontEndController.java b/src/main/java/pl/exchanger/exchanger/controller/FrontEndController.java[m
[1mindex f879108..1424db7 100644[m
[1m--- a/src/main/java/pl/exchanger/exchanger/controller/FrontEndController.java[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/controller/FrontEndController.java[m
[36m@@ -25,7 +25,7 @@[m [mpublic class FrontEndController {[m
 [m
     @PostMapping("/exchanger")[m
     String postEx(@ModelAttribute CurrencyExchanger exchanger, ModelMap map) {[m
[31m-        exchanger.exchange();[m
[32m+[m[32m//        exchanger.exchange();[m
         map.put("exchanger",exchanger);[m
         System.out.println(exchanger.getAmount());[m
         System.out.println(exchanger.getExchangeRatio());[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/dto/ExchangeDto.java b/src/main/java/pl/exchanger/exchanger/dto/ExchangeDto.java[m
[1mindex 7bfdc11..160e924 100644[m
[1m--- a/src/main/java/pl/exchanger/exchanger/dto/ExchangeDto.java[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/dto/ExchangeDto.java[m
[36m@@ -1,11 +1,15 @@[m
 package pl.exchanger.exchanger.dto;[m
 [m
[32m+[m[32mimport lombok.Data;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.CurrencyExchanger;[m
 import pl.exchanger.exchanger.model.currency.ExchangeRequest;[m
 [m
[32m+[m[32m@Data[m
 public class ExchangeDto {[m
 [m
 [m
     private ExchangeRequest exchangeRequest;[m
[32m+[m[32m    private CurrencyExchanger exchanger;[m
 [m
 [m
 }[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/dto/mapping/ExchangeMapper.java b/src/main/java/pl/exchanger/exchanger/dto/mapping/ExchangeMapper.java[m
[1mnew file mode 100644[m
[1mindex 0000000..69f51ee[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/dto/mapping/ExchangeMapper.java[m
[36m@@ -0,0 +1,50 @@[m
[32m+[m[32mpackage pl.exchanger.exchanger.dto.mapping;[m
[32m+[m
[32m+[m[32mimport lombok.Data;[m
[32m+[m[32mimport lombok.NoArgsConstructor;[m
[32m+[m[32mimport org.springframework.beans.factory.annotation.Autowired;[m
[32m+[m[32mimport org.springframework.stereotype.Component;[m
[32m+[m[32mimport pl.exchanger.exchanger.dto.ExchangeDto;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.Currency;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.CurrencyExchanger;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.ExchangeRequest;[m
[32m+[m[32mimport pl.exchanger.exchanger.repository.CurrencyRepository;[m
[32m+[m[32mimport pl.exchanger.exchanger.repository.ExchangeRepository;[m
[32m+[m
[32m+[m[32mimport java.util.Date;[m
[32m+[m
[32m+[m[32m@Component[m
[32m+[m[32m@Data[m
[32m+[m[32m@NoArgsConstructor[m
[32m+[m[32mpublic class ExchangeMapper {[m
[32m+[m
[32m+[m[32m    @Autowired[m
[32m+[m[32m    CurrencyRepository currencyRepository;[m
[32m+[m
[32m+[m
[32m+[m[32m    public CurrencyExchanger mapToExchangeDto(ExchangeRequest exchangeRequest) {[m
[32m+[m
[32m+[m[32m        ExchangeDto exchangeDto = new ExchangeDto();[m
[32m+[m[32m        exchangeRequest.setDateOfQuotation(new Date());[m
[32m+[m
[32m+[m[32m//        Currency buy = new Currency();[m
[32m+[m[32m//        Currency sell = new Currency();[m
[32m+[m[32m//[m
[32m+[m[32m//[m
[32m+[m[32m//        buy.setCurrencyType(exchangeRequest.getBuy());[m
[32m+[m[32m//        buy.downloadCourse();[m
[32m+[m[32m//[m
[32m+[m[32m//        sell.setCurrencyType(exchangeRequest.getSell());[m
[32m+[m[32m//        sell.downloadCourse();[m
[32m+[m
[32m+[m[32m        CurrencyExchanger exchanger = new CurrencyExchanger();[m
[32m+[m[32m        exchanger.exchange(exchangeRequest);[m
[32m+[m[32m        exchangeDto.setExchanger(exchanger);[m
[32m+[m
[32m+[m
[32m+[m
[32m+[m[32m        return exchangeDto.getExchanger();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/dto/mapping/mapExchangeDpo.java b/src/main/java/pl/exchanger/exchanger/dto/mapping/mapExchangeDpo.java[m
[1mdeleted file mode 100644[m
[1mindex 2ffce9c..0000000[m
[1m--- a/src/main/java/pl/exchanger/exchanger/dto/mapping/mapExchangeDpo.java[m
[1m+++ /dev/null[m
[36m@@ -1,11 +0,0 @@[m
[31m-package pl.exchanger.exchanger.dto.mapping;[m
[31m-[m
[31m-import pl.exchanger.exchanger.dto.ExchangeDto;[m
[31m-[m
[31m-public class mapExchangeDpo {[m
[31m-[m
[31m-    ExchangeDto exchangeDto = new ExchangeDto();[m
[31m-[m
[31m-[m
[31m-[m
[31m-}[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/model/currency/Currency.java b/src/main/java/pl/exchanger/exchanger/model/currency/Currency.java[m
[1mindex 1f4d523..704b504 100644[m
[1m--- a/src/main/java/pl/exchanger/exchanger/model/currency/Currency.java[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/model/currency/Currency.java[m
[36m@@ -1,5 +1,7 @@[m
 package pl.exchanger.exchanger.model.currency;[m
 [m
[32m+[m[32mimport com.fasterxml.jackson.annotation.JsonIgnore;[m
[32m+[m[32mimport com.fasterxml.jackson.annotation.JsonProperty;[m
 import lombok.Data;[m
 import lombok.NoArgsConstructor;[m
 import org.json.simple.JSONArray;[m
[36m@@ -7,68 +9,74 @@[m [mimport org.json.simple.JSONObject;[m
 import org.json.simple.parser.JSONParser;[m
 import org.json.simple.parser.ParseException;[m
 [m
[31m-import javax.persistence.Entity;[m
[31m-import javax.persistence.GeneratedValue;[m
[31m-import javax.persistence.GenerationType;[m
[31m-import javax.persistence.Id;[m
[32m+[m[32mimport javax.persistence.*;[m
 import java.io.BufferedReader;[m
 import java.io.IOException;[m
 import java.io.InputStreamReader;[m
 import java.net.HttpURLConnection;[m
 import java.net.MalformedURLException;[m
 import java.net.URL;[m
[31m-import java.time.LocalDateTime;[m
 import java.util.Date;[m
 [m
 @Entity[m
 @Data[m
 @NoArgsConstructor[m
[32m+[m
 public class Currency {[m
 [m
     @Id[m
     @GeneratedValue(strategy = GenerationType.IDENTITY)[m
     Long id;[m
 [m
[31m-    private CurrencyType currency;[m
[32m+[m[32m    private CurrencyType currencyType;[m
 [m
     private double course;[m
 [m
[31m-    private LocalDateTime downloadDate;[m
[32m+[m[32m    private Date downloadDate;[m
 [m
[32m+[m[32m    @JsonIgnore[m
[32m+[m[32m    @JoinColumn[m
[32m+[m[32m    @ManyToOne[m
[32m+[m[32m    private CurrencyExchanger exchanger;[m
 [m
[31m-     public void downloadCourse() {[m
[32m+[m[32m    public Currency(CurrencyType currencyType) {[m
[32m+[m[32m        this.currencyType = currencyType;[m
[32m+[m[32m    }[m
 [m
[31m-         String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency;[m
[31m-        double course = 0;[m
[32m+[m[32m    public void downloadCourse() {[m
 [m
[31m-        try {[m
[32m+[m[32m        this.downloadDate = new Date();[m
 [m
[31m-            URL sellLink = new URL(url);[m
[31m-            HttpURLConnection connection = (HttpURLConnection) sellLink.openConnection();[m
[31m-            System.out.println(connection.getResponseCode());[m
[32m+[m[32m//        double course = 0;[m
 [m
[31m-            BufferedReader sellBufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));[m
[32m+[m[32m        if (currencyType.equals(CurrencyType.PLN)) {[m
[32m+[m[32m            this.course = 1.0;[m
[32m+[m[32m        } else {[m
 [m
[31m-            JSONParser parser = new JSONParser();[m
[32m+[m[32m            try {[m
[32m+[m[32m                String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyType;[m
 [m
[31m-            JSONObject jsonObject = (JSONObject) parser.parse(sellBufferedReader);[m
[31m-            JSONArray array = (JSONArray) jsonObject.get("rates");[m
[31m-            JSONObject mid = (JSONObject) array.get(0);[m
[32m+[m[32m                URL sellLink = new URL(url);[m
[32m+[m[32m                HttpURLConnection connection = (HttpURLConnection) sellLink.openConnection();[m
[32m+[m[32m                System.out.println(connection.getResponseCode());[m
 [m
[31m-            course = ((double) mid.get("mid"));[m
[31m-            course = Math.round(course * 1000) / 1000.0;[m
[32m+[m[32m                BufferedReader sellBufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));[m
[32m+[m[32m                JSONParser parser = new JSONParser();[m
 [m
[31m-        } catch (MalformedURLException e) {[m
[31m-            e.printStackTrace();[m
[31m-        } catch (IOException e) {[m
[31m-            e.printStackTrace();[m
[31m-        } catch (ParseException e) {[m
[31m-            e.printStackTrace();[m
[31m-        }[m
[32m+[m[32m                JSONObject jsonObject = (JSONObject) parser.parse(sellBufferedReader);[m
[32m+[m[32m                JSONArray array = (JSONArray) jsonObject.get("rates");[m
[32m+[m[32m                JSONObject mid = (JSONObject) array.get(0);[m
 [m
[31m-        this.downloadDate = LocalDateTime.now();[m
[32m+[m[32m                course = ((double) mid.get("mid"));[m
[32m+[m[32m                course = Math.round(course * 1000) / 1000.0;[m
 [m
[31m-        this.course=course;[m
[32m+[m[32m            } catch (MalformedURLException e) {[m
[32m+[m[32m                e.printStackTrace();[m
[32m+[m[32m            } catch (IOException e) {[m
[32m+[m[32m                e.printStackTrace();[m
[32m+[m[32m            } catch (ParseException e) {[m
[32m+[m[32m                e.printStackTrace();[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
     }[m
[31m-[m
[31m-}[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/model/currency/CurrencyExchanger.java b/src/main/java/pl/exchanger/exchanger/model/currency/CurrencyExchanger.java[m
[1mindex 7fcd8da..de80c42 100644[m
[1m--- a/src/main/java/pl/exchanger/exchanger/model/currency/CurrencyExchanger.java[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/model/currency/CurrencyExchanger.java[m
[36m@@ -1,56 +1,58 @@[m
 package pl.exchanger.exchanger.model.currency;[m
 [m
[31m-import lombok.AllArgsConstructor;[m
[32m+[m
 import lombok.Data;[m
 import lombok.NoArgsConstructor;[m
[31m-import lombok.RequiredArgsConstructor;[m
[32m+[m[32mimport org.springframework.stereotype.Component;[m
[32m+[m
 [m
[32m+[m[32mimport javax.persistence.*;[m
[32m+[m[32mimport java.util.ArrayList;[m
[32m+[m[32mimport java.util.List;[m
 [m
[31m-import javax.persistence.GeneratedValue;[m
[31m-import javax.persistence.GenerationType;[m
[31m-import javax.persistence.Id;[m
[31m-import javax.persistence.Table;[m
 [m
[32m+[m[32m@Entity[m
 @Data[m
 @NoArgsConstructor[m
[31m-@RequiredArgsConstructor[m
[31m-@AllArgsConstructor[m
[31m-@Table(name = "exchanges")[m
 public class CurrencyExchanger {[m
 [m
     @Id[m
     @GeneratedValue(strategy = GenerationType.IDENTITY)[m
     long id;[m
 [m
[31m-    Currency sell = new Currency();[m
[31m-    Currency buy = new Currency();[m
[31m-[m
     double amount;[m
     double exchangeRatio;[m
     double payment;[m
 [m
[31m-    public void exchange() {[m
[31m-[m
[31m-        if (!sell.getCurrency().equals(CurrencyType.PLN)) {[m
[31m-            sell. downloadCourse();[m
[31m-        } else {[m
[31m-            sell.setCourse(1.0);[m
[31m-        }[m
[32m+[m[32m    @OneToMany(mappedBy = "exchanger", cascade = CascadeType.ALL)[m
[32m+[m[32m    private List<Currency> currencies = new ArrayList<>();[m
 [m
[31m-        if (!buy.getCurrency().equals(CurrencyType.PLN)) {[m
[31m-            buy.downloadCourse();[m
[32m+[m[32m    public void exchange(ExchangeRequest request) {[m
 [m
[31m-        } else {[m
[31m-            buy.setCourse(1.0);[m
[31m-        }[m
[32m+[m[32m        Currency sell = new Currency();[m
[32m+[m[32m        sell.setCurrencyType(request.getSell());[m
[32m+[m[32m        sell.setExchanger(this);[m
 [m
[31m-        exchangeRatio = Math.round((sell.getCourse() / buy.getCourse()) * 1000) / 1000.0;[m
[31m-        payment = Math.round((amount * exchangeRatio) * 100) / 100.0;[m
[32m+[m[32m        Currency buy = new Currency();[m
[32m+[m[32m        buy.setCurrencyType(request.getBuy());[m
[32m+[m[32m        buy.setExchanger(this);[m
 [m
[32m+[m[32m        currencies.add(sell);[m
[32m+[m[32m        currencies.add(buy);[m
 [m
[31m-    }[m
[32m+[m[32m        for (Currency currency : currencies) {[m
 [m
[32m+[m[32m            if (currency.getCurrencyType().equals(CurrencyType.PLN)) {[m
[32m+[m[32m                currency.setCourse(1.0);[m
[32m+[m[32m            } else {[m
[32m+[m[32m                currency.downloadCourse();[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
 [m
[32m+[m[32m        amount = request.getAmount();[m
[32m+[m[32m        exchangeRatio = Math.round((currencies.get(0).getCourse() / currencies.get(1).getCourse()) * 1000) / 1000.0;[m
[32m+[m[32m        payment = Math.round((amount * exchangeRatio) * 100) / 100.0;[m
[32m+[m[32m    }[m
 [m
     public double getAmount() {[m
         return amount;[m
[36m@@ -64,22 +66,6 @@[m [mpublic class CurrencyExchanger {[m
         return payment;[m
     }[m
 [m
[31m-    public void setSell(Currency sell) {[m
[31m-        this.sell = sell;[m
[31m-    }[m
[31m-[m
[31m-    public void setSellCurrency(CurrencyType sell) {[m
[31m-        this.sell.setCurrency(sell);[m
[31m-    }[m
[31m-[m
[31m-    public void setBuy(Currency buy) {[m
[31m-        this.buy = buy;[m
[31m-    }[m
[31m-[m
[31m-    public void setBuyCurrency(CurrencyType buy) {[m
[31m-        this.buy.setCurrency(buy);[m
[31m-    }[m
[31m-[m
     public void setAmount(double amount) {[m
         this.amount = amount;[m
     }[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/model/currency/ExchangeRequest.java b/src/main/java/pl/exchanger/exchanger/model/currency/ExchangeRequest.java[m
[1mindex 9668160..b2dda8c 100644[m
[1m--- a/src/main/java/pl/exchanger/exchanger/model/currency/ExchangeRequest.java[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/model/currency/ExchangeRequest.java[m
[36m@@ -1,15 +1,15 @@[m
 package pl.exchanger.exchanger.model.currency;[m
 import lombok.Data;[m
 import lombok.NoArgsConstructor;[m
[32m+[m[32mimport org.springframework.stereotype.Component;[m
 import pl.exchanger.exchanger.model.currency.CurrencyType;[m
 [m
 import javax.persistence.Entity;[m
 import java.util.Date;[m
 [m
[32m+[m[32m@Component[m
 @Data[m
[31m-@Entity[m
 @NoArgsConstructor[m
[31m-[m
 public class ExchangeRequest {[m
 [m
     CurrencyType sell;[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/repository/CurrencyRepository.java b/src/main/java/pl/exchanger/exchanger/repository/CurrencyRepository.java[m
[1mnew file mode 100644[m
[1mindex 0000000..e0682df[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/repository/CurrencyRepository.java[m
[36m@@ -0,0 +1,10 @@[m
[32m+[m[32mpackage pl.exchanger.exchanger.repository;[m
[32m+[m
[32m+[m[32mimport org.springframework.data.repository.CrudRepository;[m
[32m+[m[32mimport org.springframework.stereotype.Repository;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.Currency;[m
[32m+[m
[32m+[m[32m@Repository[m
[32m+[m[32mpublic interface CurrencyRepository extends CrudRepository<Currency,Long> {[m
[32m+[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/java/pl/exchanger/exchanger/repository/ExchangeRepository.java b/src/main/java/pl/exchanger/exchanger/repository/ExchangeRepository.java[m
[1mnew file mode 100644[m
[1mindex 0000000..ec5d499[m
[1m--- /dev/null[m
[1m+++ b/src/main/java/pl/exchanger/exchanger/repository/ExchangeRepository.java[m
[36m@@ -0,0 +1,10 @@[m
[32m+[m[32mpackage pl.exchanger.exchanger.repository;[m
[32m+[m
[32m+[m[32mimport org.springframework.data.jpa.repository.JpaRepository;[m
[32m+[m[32mimport org.springframework.data.repository.CrudRepository;[m
[32m+[m[32mimport org.springframework.stereotype.Repository;[m
[32m+[m[32mimport pl.exchanger.exchanger.model.currency.CurrencyExchanger;[m
[32m+[m
[32m+[m[32m@Repository[m
[32m+[m[32mpublic interface ExchangeRepository extends CrudRepository<CurrencyExchanger,Long> {[m
[32m+[m[32m}[m
[1mdiff --git a/src/main/resources/application.properties b/src/main/resources/application.properties[m
[1mindex 2ad1597..ca4cd99 100644[m
[1m--- a/src/main/resources/application.properties[m
[1m+++ b/src/main/resources/application.properties[m
[36m@@ -4,5 +4,5 @@[m [mspring.datasource.username=30953014_exc[m
 spring.datasource.password=MrX2OdoM[m
 spring.datasource.url=jdbc:mysql://sql.serwer1933526.home.pl/30953014_exc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC[m
 spring.jpa.show-sql=true[m
[31m-spring.jpa.hibernate.ddl-auto=update[m
[32m+[m[32mspring.jpa.hibernate.ddl-auto=create-drop[m
 logging.level.org.hibernate.SQL=info[m
\ No newline at end of file[m
