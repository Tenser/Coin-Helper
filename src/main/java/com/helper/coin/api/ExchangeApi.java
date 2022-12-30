package com.helper.coin.api;

import java.util.List;
import java.util.Map;

public interface ExchangeApi {
    public String sendGetRequest(String path, Map<String, String> params) throws Exception;
    Map<String, Double> getMinuteCandle(int unit, String coinName, String currency) throws Exception;
    List<Map<String, String>> getMarketAll() throws Exception;
}
