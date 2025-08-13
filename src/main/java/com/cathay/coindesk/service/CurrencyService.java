package com.cathay.coindesk.service;

import com.cathay.coindesk.entity.CurrencyEntity;
import com.cathay.coindesk.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    public List<CurrencyEntity> findAll() {
        return repository.findAll();
    }

    public Optional<CurrencyEntity> findByCode(String code) {
        return repository.findByCode(code);
    }

    public CurrencyEntity save(CurrencyEntity currencyEntity) {
        return repository.save(currencyEntity);
    }

    public CurrencyEntity saveOrUpdate(CurrencyEntity newEntity) {
        Optional<CurrencyEntity> existingOpt = repository.findByCode(newEntity.getCode());

        if (existingOpt.isPresent()) {
            CurrencyEntity existing = existingOpt.get();
            existing.setRate(newEntity.getRate());
            existing.setRate_float(newEntity.getRate_float());
            existing.setDescription(newEntity.getDescription());
            existing.setUpdatedISO(newEntity.getUpdatedISO());
            return repository.save(existing);//update
        } else {
            return repository.save(newEntity);//插入新資料
        }
    }

    public void deleteByCode(String code) {
        repository.deleteByCode(code);
    }


}
