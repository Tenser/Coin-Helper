package com.helper.coin.api.upbit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpbitApi{

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
            System.out.println(httpget.toString());
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


}
