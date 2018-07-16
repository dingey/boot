package com.d.config;

import com.di.kit.ClassUtil;
import com.di.kit.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.di.kit.ClassUtil.getFieldArrayType;

public class FormModelResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(FormModel.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FormModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Constructor<?> constructor = parameter.getConstructor();
        return instance(null, parameter.getParameterType(), request);
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FormModel {
    }

    private <T> Object instance(String prefix, Class<T> t, HttpServletRequest req) {
        try {
            if (t.getConstructor().getParameterCount() > 0) {
                throw new RuntimeException("类" + t.getName() + "必须有无参构造方法。");
            } else {
                Object o = t.newInstance();
                for (Field f : ClassUtil.getDeclaredFields(t)) {
                    setFieldValue(prefix, 0, f, o, req.getParameterMap());
                }
                return o;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldValue(String prefix, int i, Field f, Object o, Map<String, String[]> reqMap) {
        String name;
        if (f.getType().isArray() || f.getType() == List.class || f.getType() == ArrayList.class) {
            name = (prefix == null ? "" : (prefix + ".")) + StringUtil.snakeCase(f.getName()) + "[" + i + "]";
        } else {
            name = (prefix == null ? "" : (prefix + ".")) + StringUtil.snakeCase(f.getName());
        }
        String[] values = reqMap.get(name);
        if (values == null || values.length == 0)
            return;
        try {
            if (!f.isAccessible()) f.setAccessible(true);
            if (f.getType() == boolean.class || f.getType() == java.lang.Boolean.class) {
                f.set(o, Boolean.valueOf(values[i]));
            } else if (f.getType() == byte.class || f.getType() == java.lang.Byte.class) {
                f.set(o, Byte.valueOf(values[i]));
            } else if (f.getType() == short.class || f.getType() == java.lang.Short.class) {
                f.set(o, Short.valueOf(values[i]));
            } else if (f.getType() == int.class || f.getType() == java.lang.Integer.class) {
                f.set(o, Integer.valueOf(values[i]));
            } else if (f.getType() == long.class || f.getType() == java.lang.Long.class) {
                f.set(o, Long.valueOf(values[i]));
            } else if (f.getType() == double.class || f.getType() == java.lang.Double.class) {
                f.set(o, Double.valueOf(values[i]));
            } else if (f.getType() == float.class || f.getType() == java.lang.Float.class) {
                f.set(o, Float.valueOf(values[i]));
            } else if (f.getType() == char.class || f.getType() == java.lang.Character.class) {
                f.set(o, values[i].charAt(0));
            } else if (f.getType() == BigDecimal.class) {
                f.set(o, new BigDecimal(values[i]));
            } else if (f.getType() == java.lang.String.class) {
                f.set(o, values[i]);
            } else if (f.getType() == java.sql.Date.class || f.getType() == java.sql.Time.class || f.getType() == java.sql.Timestamp.class) {
            } else if (f.getType() == java.util.Date.class) {
                try {
                    f.set(o, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[i]));
                } catch (ParseException e) {
                    f.set(o, new SimpleDateFormat("yyyy-MM-dd").parse(values[i]));
                }
            } else if (f.getType() == java.util.List.class || f.getType() == java.util.ArrayList.class) {
                Type type = f.getGenericType();
                ParameterizedType pt = (ParameterizedType) type;
                Type type2 = pt.getActualTypeArguments()[0];
                String typeName = type2.getTypeName();
                List<Object> os_ = new ArrayList<>();
                for (int j = 0; j < count((prefix == null ? "" : prefix) + StringUtil.snakeCase(f.getName()), reqMap.keySet()); j++) {
                    Object o0 = Class.forName(typeName).newInstance();
                    for (Field ff : ClassUtil.getDeclaredFields(o0.getClass())) {
                        setFieldValue(prefix + "[" + j + "]", j, ff, o0, reqMap);
                    }
                    os_.add(o0);
                }
                f.set(o, os_);
            } else if (f.getType().isArray()) {
                Class<?> type = getFieldArrayType(f);
                if (type == String.class) {
                    f.set(o, values);
                } else {
                    Object[] tos = new Object[count((prefix == null ? "" : prefix) + StringUtil.snakeCase(f.getName()), reqMap.keySet())];
                    for (int j = 0; j < tos.length; j++) {
                        Object o0 = Class.forName(type.getName()).newInstance();
                        for (Field f1 : ClassUtil.getDeclaredFields(o0.getClass())) {
                            setFieldValue(prefix, j, f1, o0, reqMap);
                        }
                        tos[i] = o0;
                    }
                    f.set(o, tos);
                }
            } else if (f.getType() != null) {
                Object fo = f.getType().newInstance();
                for (Field f1 : ClassUtil.getDeclaredFields(fo.getClass())) {
                    setFieldValue(prefix, 0, f1, fo, reqMap);
                }
                f.set(o, fo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    int count(String k, Collection<String> ks) {
        int i = 0;
        for (String s : ks) {
            if (s.startsWith(k + "["))
                i++;
        }
        return i;
    }
}
