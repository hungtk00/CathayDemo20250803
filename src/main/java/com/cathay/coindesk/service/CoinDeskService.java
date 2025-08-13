package com.cathay.coindesk.service;

import com.cathay.coindesk.entity.CurrencyEntity;
import com.cathay.coindesk.repository.CurrencyRepository;
import com.cathay.coindesk.response.CoinDeskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoinDeskService {
    private static final String API_URL = "https://kengp3.github.io/blog/coindesk.json";
    @Autowired
    private CurrencyRepository repository;

    public CoinDeskResponse callCoinDeskApi() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL, CoinDeskResponse.class);
    }

    public Optional<CurrencyEntity> findByCode(String code) {
        return repository.findByCode(code);
    }

    public List<String> callCoinDeskApiAndUpdate(){
        List<String> returnLsit = new ArrayList<>();
        CoinDeskResponse response = callCoinDeskApi();
        if(null != response && null != response.getBpi() && !response.getBpi().isEmpty()){
            response.getBpi().forEach((code, currencyDTO) ->{
                Optional<CurrencyEntity> existingOpt = repository.findByCode(currencyDTO.getCode());
                if (existingOpt.isPresent()) {
                    CurrencyEntity existing = existingOpt.get();
                    existing.setRate(currencyDTO.getRate());
                    existing.setRate_float(currencyDTO.getRate_float());
                    existing.setDescription(currencyDTO.getDescription() + " new");
                    existing.setUpdatedISO(convertToFormattedDateString(response.getTime().getUpdatedISO()));
                    repository.save(existing);//update
                    returnLsit.add(existing.getCode() + ", 資料更新成功");
                } else {
                    CurrencyEntity currencyEntity = new CurrencyEntity();
                    currencyEntity.setCode(currencyDTO.getCode());
                    currencyEntity.setRate(currencyDTO.getRate());
                    currencyEntity.setRate_float(currencyDTO.getRate_float());
                    currencyEntity.setDescription(currencyDTO.getDescription());
                    currencyEntity.setUpdatedISO(convertToFormattedDateString(response.getTime().getUpdatedISO()));
                    repository.save(currencyEntity);//插入新資料
                    returnLsit.add(currencyDTO.getCode()+", 資料新增成功");
                }
            });
            return returnLsit;
        }else{
            returnLsit.add("查詢callCoinDeskApi異常");
            return returnLsit;
        }
    }

    /**
     * 日期格式轉換 to yyyy/MM/dd HH:mm:ss
     * @param isoInput
     * @return
     */
    public String convertToFormattedDateString(String isoInput) {
        // 解析帶有時區的 ISO 日期
        OffsetDateTime odt = OffsetDateTime.parse(isoInput);

        // 轉成指定格式字串（不含時區）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return odt.format(formatter);
    }
}
