package com.helper.coin.api.binance;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.helper.coin.api.ExchangeApi;
import com.helper.coin.api.ExchangeRate;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BinanceApi implements ExchangeApi {
    private String accessKey;
    private String secretKey;
    private String ipAddress;

    public BinanceApi(String accessKey, String secretKey, String ipAddress){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.ipAddress = ipAddress;
    }

    public String getAuthenticationToken(String queryString) throws NoSuchAlgorithmException, UnsupportedEncodingException {

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

    public Map<String, Double> getMinuteCandle(int unit, String coinName, String currency) throws Exception{
        int unitStandard = 5;
        Map<String, String> params = new HashMap<>();
        params.put("symbol", coinName+currency);
        params.put("interval", Integer.toString(unitStandard) + "m");
        params.put("limit", Integer.toString(unit * 2 / unitStandard));
        JSONArray jsonArray = (JSONArray)new JSONParser().parse(sendGetRequest("/api/v3/klines", params));
        //System.out.println(jsonArray.toString());
        //System.out.println(jsonArray.toString());
        Double beforeVolume = 0.0;
        Double nowVolume = 0.0;
        Double beforeAmount = 0.0;
        Double nowAmount = 0.0;
        for(int i=0;i<unit/unitStandard;i++){
            Double volume = Double.parseDouble((String)(((JSONArray)jsonArray.get(i)).get(5)));
            beforeVolume += volume;
            beforeAmount += Double.parseDouble((String)(((JSONArray)jsonArray.get(i)).get(7))) * ExchangeRate.exchangeRate;
        }
        for(int i=unit/unitStandard;i<unit*2/unitStandard;i++){
            Double volume = Double.parseDouble((String)(((JSONArray)jsonArray.get(i)).get(5)));
            nowVolume += volume;
            nowAmount += Double.parseDouble((String)(((JSONArray)jsonArray.get(i)).get(7))) * ExchangeRate.exchangeRate;
        }
        Map<String, Double> res = new HashMap<>();
        res.put("beforeVolume", Math.round(beforeVolume * 100) / 100.0);
        res.put("nowVolume", Math.round(nowVolume * 100) / 100.0);
        res.put("beforePrice", Double.parseDouble((String)(((JSONArray)jsonArray.get(unit/unitStandard-1)).get(4))));
        res.put("nowPrice", Double.parseDouble((String)(((JSONArray)jsonArray.get(unit*2/unitStandard-1)).get(4))));
        res.put("beforeAmount", Math.round(beforeAmount * 100) / 100.0);
        res.put("nowAmount", Math.round(nowAmount * 100) / 100.0);
        return res;
    }

    public List<Map<String, String>> getMarketAll() throws Exception {
        JSONObject jsonObject = (JSONObject)new JSONParser().parse(sendGetRequest("/api/v3/exchangeInfo", new HashMap<String, String>()));
        JSONArray jsonArray = (JSONArray) jsonObject.get("symbols");
        System.out.println(jsonArray.toString().substring(0, 100));
        List<Map<String, String>> res = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            Map<String, String> market = new HashMap<>();
            String baseAsset = (String)((JSONObject)jsonArray.get(i)).get("baseAsset");
            String quoteAsset = (String)((JSONObject)jsonArray.get(i)).get("quoteAsset");
            if (!quoteAsset.equals("USDT") || baseAsset.length() > 2 && baseAsset.substring(baseAsset.length()-2).equals("UP") || baseAsset.length() > 4 && baseAsset.substring(baseAsset.length()-4).equals("DOWN")) continue;
            market.put("name", baseAsset);
            market.put("currency", quoteAsset);
            res.add(market);
        }
        return res;
    }
}
