package com.helper.coin.api;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;

public class ExchangeRate {
    public static Double exchangeRate;

    public static void setExchangeRate() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response;

        try {
            RequestBuilder rb = RequestBuilder.get()
                    .setUri(new URI("https://quotation-api-cdn.dunamu.com/v1/forex/recent?codes=FRX.KRWUSD"));
            HttpUriRequest httpget = rb.build();
            //System.out.println(httpget.toString());
            response = httpclient.execute(httpget);
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(EntityUtils.toString(response.getEntity()));
            exchangeRate = (Double) ((JSONObject)jsonArray.get(0)).get("basePrice");
            try {
                return;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
