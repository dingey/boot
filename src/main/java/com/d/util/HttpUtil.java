package com.d.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url, Map<?, ?> args) {
        if (args != null && !args.isEmpty()) {
            if (!url.contains("?")) {
                url += "?";
            }
            StringBuilder urlBuilder = new StringBuilder(url);
            for (Object k : args.keySet()) {
                urlBuilder.append(String.valueOf(k)).append("=").append(String.valueOf(args.get(k))).append("&");
            }
            url = urlBuilder.deleteCharAt(url.length() - 1).toString();
        }
        HttpEntity entity = request(url);
        if (entity != null) {
            try {
                return EntityUtils.toString(entity, "utf-8");
            } catch (ParseException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String post(String url, Object arg) {
        try {
            HttpEntity requestEntity;
            if (arg instanceof Map) {
                Map<?, ?> args = (Map) arg;
                List<NameValuePair> nvps = new ArrayList<>();
                for (Object key : args.keySet()) {
                    nvps.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(args.get(key))));
                }
                requestEntity = new UrlEncodedFormEntity(nvps, "utf-8");
            } else if (arg instanceof String) {
                requestEntity = new StringEntity(arg.toString(), "utf-8");
            } else {
                requestEntity = new StringEntity(JsonUtil.singleton().toJson(arg), "utf-8");
            }
            HttpEntity entity = request(url, requestEntity);
            if (entity != null) {
                return EntityUtils.toString(entity, "utf-8");
            }
        } catch (ParseException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static HttpEntity request(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return new ByteArrayEntity(EntityUtils.toByteArray(entity), ContentType.get(entity));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return new ByteArrayEntity(new byte[0]);
    }

    public static HttpEntity request(String url, String str) {
        return request(url, new StringEntity(str, "utf-8"));
    }

    public static HttpEntity request(String url, HttpEntity httpEntity) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(httpEntity);
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return new ByteArrayEntity(EntityUtils.toByteArray(entity), ContentType.get(entity));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null)
                    response.close();
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return new ByteArrayEntity(new byte[0]);
    }

    public static void main(String[] args)
            throws UnsupportedOperationException, IOException {
        HttpEntity request = request("http://localhost:8090/hi", "name=alice");
        System.out.println(request.isStreaming());
        System.out.println(EntityUtils.toString(request));
    }
}
