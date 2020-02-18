package pl.exchanger.exchanger.dto.mapping;

import org.springframework.stereotype.Component;
import pl.exchanger.exchanger.dto.ListDto;
import pl.exchanger.exchanger.model.currency.Currency;
import pl.exchanger.exchanger.model.currency.CurrencyType;


import java.util.List;
@Component
public class ListMapper {



    public List<Currency> maptToListDto(List<CurrencyType> currencyTypeList) {

        ListDto listDto = new ListDto();

        for (CurrencyType currencyType : currencyTypeList) {

            Currency currency = new Currency();
            currency.setCurrencyType(currencyType);
            currency.downloadCourse();
            listDto.getCurrencyList().add(currency);
        }
        return listDto.getCurrencyList();

    }
}
