package com.helper.coin.api;

import java.util.List;
import java.util.Map;

public interface ExchangeApi {
    String sendGetRequest(String path, Map<String, String> params) throws Exception;
    List<Map<String, Double>> getMinuteCandle(String coinName, String currency) throws Exception;
    List<Map<String, String>> getMarketAll() throws Exception;
}
