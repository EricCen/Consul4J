package org.consul4j.client;

import com.google.common.base.Preconditions;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by Eric Cen on 2016/10/4.
 */
public class ConsulClient {
    private final String consulHost;
    private final int consulPort;
    private final OkHttpClient okHttpClient;
    private static final String CONSUL_KEY_VALUE_V1_PREFIX = "v1";
    private static final String CONSUL_KEY_VALUE_KV_PREFIX = "kv";

    public ConsulClient(String consulHost, int consulPort) {
        Preconditions.checkNotNull(consulHost);
        Preconditions.checkNotNull(consulPort);
        this.consulHost = consulHost;
        this.consulPort = consulPort;
        this.okHttpClient = new OkHttpClient();
    }

    public String getValueByKeyFromConsul(String key){
        String value;
        Request.Builder requestBuilder = createRequestBuilder(key);
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject jsonObject = (JSONObject)JSONValue.parse(responseBody.substring(1, responseBody.length() - 1));
            String encodedValue = (String)jsonObject.get("Value");
            value = new String(Base64.getDecoder().decode(encodedValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    public boolean addKeyValueToConsul(String key, String value){
        Request.Builder requestBuilder = createRequestBuilder(key);
        requestBuilder.method("PUT", RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), value));
        Request request = requestBuilder.build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(!response.isSuccessful()){
                return false;
            }
        } catch (IOException e) {
           return false;
        }
        return true;
    }

    public boolean deleteKeyValueFromConsul(String key){
        Request.Builder requestBuilder = createRequestBuilder(key);
        requestBuilder.method("DELETE", null);
        Request request = requestBuilder.build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(!response.isSuccessful()){
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Request.Builder createRequestBuilder(String key){
        Request.Builder requestBuilder = new Request.Builder();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(consulHost)
                .port(consulPort)
                .addPathSegment(CONSUL_KEY_VALUE_V1_PREFIX)
                .addPathSegment(CONSUL_KEY_VALUE_KV_PREFIX)
                .addPathSegment(key)
                .build();
        requestBuilder.url(httpUrl);
        return requestBuilder;
    }

}
