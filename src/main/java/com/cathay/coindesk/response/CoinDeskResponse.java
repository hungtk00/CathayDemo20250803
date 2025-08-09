package com.cathay.coindesk.response;

import java.util.Map;

public class CoinDeskResponse {
    private TimeDTO time;
    private String disclaimer;
    private Map<String, CurrencyDTO> bpi;

    public TimeDTO getTime() {
        return time;
    }

    public void setTime(TimeDTO time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Map<String, CurrencyDTO> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, CurrencyDTO> bpi) {
        this.bpi = bpi;
    }
}
