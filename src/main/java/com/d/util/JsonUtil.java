package com.d.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T fromJson(String json, Class<T> valueType) {
		try {
			return objectMapper.readValue(json, valueType);
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
			return null;
		}
	}

	public static <T> String toJson(T t) {
		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			logger.debug(e.getMessage(), e);
			return null;
		}
	}
}
