package com.helper.coin.api.upbit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.ExchangeRate;
import com.helper.coin.api.binance.BinanceApi;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpbitApi implements ExchangeApi {

    private String accessKey;
    private String secretKey;
    private String ipAddress;

    public UpbitApi(String accessKey, String secretKey, String ipAddress){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.ipAddress = ipAddress;
    }

    public String getAuthenticationToken(String queryString) throws NoSuchAlgorithmException, UnsupportedEncodingException{

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("utf8"));

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", this.accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        return authenticationToken;
    }

    public String sendGetRequest(String path, Map<String, String> params) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response;
        try {
            RequestBuilder rb = RequestBuilder.get()
                    .setUri(new URI(ipAddress+path));
            for(String key: params.keySet()) {
                rb = rb.addParameter(key, params.get(key));
            }
            HttpUriRequest httpget = rb.build();
            //System.out.println(httpget.toString());
            response = httpclient.execute(httpget);
            try {
                return EntityUtils.toString(response.getEntity());
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
    /*
    public Map<String, Double> getMinuteCandle(int unit, String coinName, String currency, int count) throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("market", currency+"-"+coinName);
        params.put("count", Integer.toString(count));
        JSONArray jsonArray = (JSONArray)new JSONParser().parse(sendGetRequest("/v1/candles/minutes/"+unit, params));
        Map<String, Double> res = new HashMap<>();
        JSONObject nowInform = (JSONObject) jsonArray.get(0);
        res.put("nowVolume", (Double) nowInform.get("candle_acc_trade_volume"));
        res.put("nowPrice", (Double) nowInform.get("trade_price"));
        JSONObject beforeInform = (JSONObject) jsonArray.get(1);
        res.put("beforeVolume", (Double) beforeInform.get("candle_acc_trade_volume"));
        res.put("beforePrice", (Double) beforeInform.get("trade_price"));
        return res;
    }
    */

    public Map<String, Double> getMinuteCandle(int unit, String coinName, String currency) throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("market", currency+"-"+coinName);
        params.put("count", Integer.toString(unit * 2));
        JSONArray jsonArray = (JSONArray)new JSONParser().parse(sendGetRequest("/v1/candles/minutes/1", params));
        //System.out.println(jsonArray.toString());
        Double beforeVolume = 0.0;
        Double nowVolume = 0.0;
        Double beforeAmount = 0.0;
        Double nowAmount = 0.0;
        for(int i=0;i<unit;i++){
            Double volume = (Double) ((JSONObject)jsonArray.get(i)).get("candle_acc_trade_volume");
            nowVolume += volume;
            nowAmount += (Double) ((JSONObject)jsonArray.get(i)).get("candle_acc_trade_price");
        }
        for(int i=unit;i<unit*2;i++){
            Double volume = (Double) ((JSONObject)jsonArray.get(i)).get("candle_acc_trade_volume");
            beforeVolume += volume;
            beforeAmount += (Double) ((JSONObject)jsonArray.get(i)).get("candle_acc_trade_price");
        }
        Map<String, Double> res = new HashMap<>();
        res.put("beforeVolume", beforeVolume);
        res.put("nowVolume", nowVolume);
        res.put("beforePrice", (Double) ((JSONObject)jsonArray.get(unit)).get("trade_price"));
        res.put("nowPrice", (Double) ((JSONObject)jsonArray.get(0)).get("trade_price"));
        res.put("beforeAmount", beforeAmount);
        res.put("nowAmount", nowAmount);
        return res;
    }

    public List<Map<String, String>> getMarketAll() throws Exception {
        JSONArray jsonArray = (JSONArray)new JSONParser().parse(sendGetRequest("/v1/market/all", new HashMap<String, String>()));
        List<Map<String, String>> res = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            Map<String, String> market = new HashMap<>();
            String[] marketSplit = ((String)((JSONObject)jsonArray.get(i)).get("market")).split("-");
            if (!marketSplit[0].equals("KRW")) continue;
            market.put("name", marketSplit[1]);
            market.put("currency", marketSplit[0]);
            res.add(market);
        }
        return res;
    }
}
