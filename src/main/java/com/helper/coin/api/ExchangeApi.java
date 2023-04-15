package com.helper.coin.api;

import java.util.List;
import java.util.Map;

public interface ExchangeApi {
    String sendGetRequest(String path, Map<String, String> params);
    List<Map<String, Object>> getMinuteCandle(String coinName, String currency, String exchange) throws Exception;
    List<Map<String, String>> getMarketAll() throws Exception;
}
