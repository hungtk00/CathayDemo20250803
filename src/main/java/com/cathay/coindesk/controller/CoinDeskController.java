package com.cathay.coindesk.controller;

import com.cathay.coindesk.response.CoinDeskResponse;
import com.cathay.coindesk.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/api/coindesk")
public class CoinDeskController {

    private static final String API_URL = "https://kengp3.github.io/blog/coindesk.json";

    @Autowired
    private CoinDeskService coinDeskService;

    /**
     * 呼叫 coindesk api
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<CoinDeskResponse> query() {
        RestTemplate restTemplate = new RestTemplate();
        CoinDeskResponse response = restTemplate.getForObject(API_URL, CoinDeskResponse.class);
        return ResponseEntity.ok(response);
    }

    /**
     * 呼叫 coindesk api 並 回寫DB
     * @return
     */
    @GetMapping("/queryAndUpdate")
    public ResponseEntity<List<String>> queryAndUpdate() {
        return ResponseEntity.ok(coinDeskService.callCoinDeskApiAndUpdate());
    }
}