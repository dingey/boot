package com.d.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.d.service.PetService;
import com.d.web.PetController;
import com.di.kit.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d.util.StringArraySerializer.Man;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.pagehelper.PageInfo;

@SuppressWarnings("all")
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private static JsonUtil instance = new JsonUtil();

    public <T> T fromJson(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            logger.debug(e.getMessage(), e);
            return null;
        }
    }

    public <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fromJsonToList(String json, Class<T> valueType) {
        return fromJsonWrapper(json, ArrayList.class, valueType);
    }

    public <E> E fromJsonWrapper(String json, Class<E> e, Class<?>... valueType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory()
                    .constructParametricType(e, valueType);
            return objectMapper.readValue(json, javaType);
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static JsonUtil build() {
        return new JsonUtil();
    }

    public JsonUtil nullIgnore() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return this;
    }

    public JsonUtil nullContain() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return this;
    }

    public JsonUtil snakeCase() {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return this;
    }

    public static JsonUtil singleton() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String json1 = "{\"list\":[{\"id\":1}]}";
        PageInfo<Man> p = singleton().fromJsonWrapper(json1, PageInfo.class, Man.class);
        System.out.println(p.getList().size());
        Man man = p.getList().get(0);
        System.out.println(singleton().toJson(man));

        String json2 = "[{\"id\":1}]";
        List<Man> ms = build().fromJsonToList(json2, Man.class);
        System.out.println(ms.size());

        Method method = ClassUtil.getDeclaredMethod(PetService.class, "list", PetController.Pet.class);
        List list = singleton().fromJsonWrapper(json2, List.class, ClassUtil.getMethodReturnGenericType(method));
    }
}
