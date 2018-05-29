package com.d.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

/**
 * json序列化
 *
 * @auther d
 */
public class StringArraySerializer extends JsonSerializer<String> {
	@Override
	public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		if (s != null && !s.isEmpty()) {
			jsonGenerator.writeObject(s.split(","));
		} else {
			jsonGenerator.writeString(s);
		}
	}

	public static class Man {
		private Integer id;
		@JsonDeserialize(using = StringArrayDeserializer.class)
		@JsonSerialize(using = StringArraySerializer.class)
		private String name;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
		Man m = new Man();
		m.setId(1);
		m.setName("abc,efg");
		String json = JsonUtil.build().toJson(m);
		System.out.println(json);
	}
}
