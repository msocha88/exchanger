package pl.exchanger.exchanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.exchanger.exchanger.model.currency.CurrencyExchanger;


@Controller
public class FrontEndController {

    @GetMapping("/")
    String index() {
                return "index";
    }


    @GetMapping("/exchanger")
    String exchanger(ModelMap map) {
       map.put("exchanger", new CurrencyExchanger());
        return "exchanger";
    }

    @PostMapping("/exchanger")
    String postEx(@ModelAttribute CurrencyExchanger exchanger, ModelMap map) {
        exchanger.exchange();
        map.put("exchanger",exchanger);
        System.out.println(exchanger.getAmount());
        System.out.println(exchanger.getExchangeRatio());
        System.out.println(exchanger.getPayment());
        return "postEx";
    }

    @GetMapping("/apiInstructions")
    String getApiInstructions(){
        return "apiInstructions";
    }
}

