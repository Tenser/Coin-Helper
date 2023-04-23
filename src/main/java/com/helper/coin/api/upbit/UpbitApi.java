package com.helper.coin.api.upbit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.ExchangeRate;
import com.helper.coin.api.binance.BinanceApi;
import com.helper.coin.service.Coin.CoinService;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;

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

    public String sendGetRequest(String path, Map<String, String> params){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response;
            try {
                RequestBuilder rb = RequestBuilder.get()
                        .setUri(new URI(ipAddress + path));
                for (String key : params.keySet()) {
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
        }catch (Exception E){
            System.out.println(E);
            return null;
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

    public List<Map<String, Object>> getMinuteCandle(String coinName, String currency, String exchange, int[] units) throws Exception{
        try {
            int unitStandard = 5;
            Map<String, String> params = new HashMap<>();
            params.put("market", currency + "-" + coinName);
            params.put("count", Integer.toString(units[units.length - 1] * 2 / unitStandard));
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(sendGetRequest("/v1/candles/minutes/" + Integer.toString(unitStandard), params));
            //System.out.println(jsonArray.toString());
            List<Map<String, Object>> ress = new ArrayList<>();
            for (int unit : units) {
                Double beforeVolume = 0.0;
                Double nowVolume = 0.0;
                Double beforeAmount = 0.0;
                Double nowAmount = 0.0;
                for (int i = 0; i < unit / unitStandard; i++) {
                    Double volume = (Double) ((JSONObject) jsonArray.get(i)).get("candle_acc_trade_volume");
                    nowVolume += volume;
                    nowAmount += (Double) ((JSONObject) jsonArray.get(i)).get("candle_acc_trade_price");
                }
                for (int i = unit / unitStandard; i < unit * 2 / unitStandard; i++) {
                    Double volume = (Double) ((JSONObject) jsonArray.get(i)).get("candle_acc_trade_volume");
                    beforeVolume += volume;
                    beforeAmount += (Double) ((JSONObject) jsonArray.get(i)).get("candle_acc_trade_price");
                }
                Map<String, Object> res = new HashMap<>();
                res.put("coinName", coinName);
                res.put("currency", currency);
                res.put("exchange", exchange);
                res.put("beforeVolume", Math.round(beforeVolume * 100) / 100.0);
                res.put("nowVolume", Math.round(nowVolume * 100) / 100.0);
                res.put("beforePrice", (Double) ((JSONObject) jsonArray.get(unit / unitStandard)).get("trade_price"));
                res.put("nowPrice", (Double) ((JSONObject) jsonArray.get(0)).get("trade_price"));
                res.put("beforeAmount", Math.round(beforeAmount * 100) / 100.0);
                res.put("nowAmount", Math.round(nowAmount * 100) / 100.0);
                res.put("modifiedDate", LocalDateTime.now());
                ress.add(res);
            }

            return ress;
        }catch (Exception E){
            System.out.println(E + "upbit");
            return null;
        }
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
