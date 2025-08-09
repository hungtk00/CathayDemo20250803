package com.cathay.coindesk.controller;

import com.cathay.coindesk.entity.CurrencyEntity;
import com.cathay.coindesk.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    /**
     * 查詢全部的CurrencyEntity資料
     * @return
     */
    @GetMapping(value = "/query")
    public ResponseEntity<List<CurrencyEntity>> query() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * 查指定code的CurrencyEntity資料
     * @param currencyEntity
     * @return
     */
    @PostMapping(value = "/findByCode")
    public ResponseEntity<CurrencyEntity> findByCode(@RequestBody CurrencyEntity currencyEntity) {
        Optional<CurrencyEntity> optional = service.findByCode(currencyEntity.getCode());
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 建立CurrencyEntity
     * @param currencyEntity
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<CurrencyEntity> create(@RequestBody CurrencyEntity currencyEntity) {
        return ResponseEntity.ok(service.saveOrUpdate(currencyEntity));
    }

    /**
     * 更新CurrencyEntity
     * @param currencyEntity
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseEntity<CurrencyEntity> update(@RequestBody CurrencyEntity currencyEntity) {
        return ResponseEntity.ok(service.saveOrUpdate(currencyEntity));
    }

    /**
     * 刪除指定code的CurrencyEntity資料
     * @param currencyEntity
     * @return
     */
    @PostMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestBody CurrencyEntity currencyEntity) {
        service.deleteByCode(currencyEntity.getCode());
        return ResponseEntity.ok("SUCCESS");
    }
}
