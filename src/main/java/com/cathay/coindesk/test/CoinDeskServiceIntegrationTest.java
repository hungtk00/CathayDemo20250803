package com.cathay.coindesk.test;

import com.cathay.coindesk.MyDemoApplication;
import com.cathay.coindesk.entity.CurrencyEntity;
import com.cathay.coindesk.response.CoinDeskResponse;
import com.cathay.coindesk.service.CoinDeskService;
import com.cathay.coindesk.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= MyDemoApplication.class)
public class CoinDeskServiceIntegrationTest {
    @Autowired
    private CoinDeskService coinDeskService;
    @Autowired
    private CurrencyService currencyService;

    @Test
    public void testCallCoinDeskApi() {
        CoinDeskResponse response = coinDeskService.callCoinDeskApi();

        assertNotNull(response);
        assertNotNull(response.getTime());
        assertNotNull(response.getBpi());

        System.out.println("Updated: " + response.getTime().getUpdated());

        response.getBpi().forEach((code, currency) -> {
            System.out.println(code + ": " + currency.getRate() + " (" + currency.getDescription() + ")" + ", rate float: " + currency.getRate_float());
        });

        assertTrue(response.getBpi().containsKey("USD"));
    }

    @Test
    public void testCallCoinDeskApiAndUpdate(){
        CoinDeskResponse response = coinDeskService.callCoinDeskApi();
        if(null != response && null != response.getBpi() && !response.getBpi().isEmpty()){
            response.getBpi().forEach((code, currencyDTO) ->{
                CurrencyEntity currencyEntity = new CurrencyEntity();
                currencyEntity.setCode(currencyDTO.getCode());
                currencyEntity.setRate("10.0123");
                currencyEntity.setRate_float(10.0123);
                currencyEntity.setDescription(currencyDTO.getDescription() + " new");
                currencyEntity.setUpdatedISO(new Date());
                currencyService.save(currencyEntity);
            });
        }
    }

    @Test
    public void testQueryCurrencyData(){
        List<CurrencyEntity> currencyEntityList = currencyService.findAll();
        currencyEntityList.forEach(data -> System.out.println(data));
    }

    @Test
    public void testCreateCurrencyData(){
        CurrencyEntity currencyEntity = new CurrencyEntity();
        //currencyEntity.setId(3L);
        currencyEntity.setCode("JPY");
        currencyEntity.setRate("57,756.298");
        currencyEntity.setDescription("Japan Dollar");
        currencyEntity.setRate_float(57756.2984);
        currencyEntity.setUpdatedISO(new Date());
        try {
            currencyService.save(currencyEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("save data="+currencyEntity);
    }

    @Test
    public void testUpdateCurrencyData(){
        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setCode("JPY");
        currencyEntity.setRate("57,756.298");
        currencyEntity.setDescription("Japan Dollar");
        currencyEntity.setRate_float(57756.2984);
        currencyEntity.setUpdatedISO(new Date());
        currencyService.save(currencyEntity);
        System.out.println("save data="+currencyEntity);
    }

    @Test
    public void testDeleteCurrencyData(){
        String deleteCode = "JPY";
        currencyService.deleteByCode(deleteCode);
        System.out.println("delete " + deleteCode + " success");
    }
}
