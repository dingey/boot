package com.d.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {
	private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	public static String get(String url) {
		return get(url, null);
	}

	public static String get(String url, Map<?, ?> param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = null;
		if (param != null && !param.isEmpty()) {
			StringBuilder s = new StringBuilder(url);
			if (url.contains("?")) {
				s.append("&");
			} else {
				s.append("?");
			}
			for (Object k : param.keySet()) {
				s.append(k).append("=").append(param.get(k)).append("&");
			}
			httpGet = new HttpGet(s.toString());
		} else {
			httpGet = new HttpGet(url);
		}
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	public static String post(String url, Map<?, ?> param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Object key : param.keySet()) {
			nvps.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(param.get(key))));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	private static String post(String url, String str, String contentType) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", contentType);
		try {
			httpPost.setEntity(new StringEntity(str));
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	public static String postStr(String url, String str) {
		return post(url, str, "text/plain");
	}

	public static String postJson(String url, String json) {
		return post(url, json, "application/json;charset=UTF-8");
	}

	public static String postXml(String url, String xml) {
		return post(url, xml, "text/xml; charset=UTF-8");
	}
}
