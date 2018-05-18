package com.d.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d.util.StringArraySerializer.Man;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

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

	@SuppressWarnings("unchecked")
	public static <T> List<T> fromJsonToList(String json, Class<T> valueType) {
		return fromJsonWrapper(json, ArrayList.class, valueType);
	}

	public static <E, T> E fromJsonWrapper(String json, Class<E> e, Class<T> valueType) {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(e, valueType);
			return objectMapper.readValue(json, javaType);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String json1 = "{\"list\":[{\"id\":1}]}";
		PageInfo<Man> p = fromJsonWrapper(json1, PageInfo.class, Man.class);
		System.out.println(p.getList().size());

		String json2 = "[{\"id\":1}]";
		List<Man> ms = fromJsonToList(json2, Man.class);
		System.out.println(ms.size());
	}
}
