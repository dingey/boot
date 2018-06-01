package com.d.util;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	private final static Logger logger = LoggerFactory.getLogger(Client.class);

	public static HttpEntity request(String url, String str) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new StringEntity(str, "utf-8"));
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity.isStreaming()) {
				return new ByteArrayEntity(EntityUtils.toByteArray(entity));
			} else {
				return new StringEntity(EntityUtils.toString(entity, "utf-8"));
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static void main(String[] args)
			throws UnsupportedOperationException, IOException {
		HttpEntity request = request("http://localhost:8090/hi", "name=alice");
		System.out.println(request.isStreaming());
		System.out.println(EntityUtils.toString(request));
	}
}
