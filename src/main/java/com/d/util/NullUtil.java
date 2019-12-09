package com.d.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author di
 */
public class NullUtil {
	public static Map<String, Object> handlerModel(Map<String, Object> m) {
		for (String k : m.keySet()) {
			if (m.get(k) == null) {
				m.put(k, "");
			} else {
				Object object = m.get(k);
				m.put(k, replaceObjectNullFields(m.get(k), object.getClass()));
			}
		}
		return m;
	}

	@SuppressWarnings({ "unchecked" })
	public static Object replaceObjectNullFields(Object o, Class<?> type) {
		if (type == Map.class || type == HashMap.class) {
			Map<Object, Object> m = (Map<Object, Object>) o;
			if (o == null) {
				return new HashMap<>();
			} else {
				for (Object key : m.keySet()) {
					Object v = m.get(key);
					if (v == null) {
						m.put(key, "");
					} else {
						m.put(key, replaceObjectNullFields(v, v.getClass()));
					}
				}
			}
			return m;
		} else if (type == Byte.class || type == Short.class || type == Integer.class
				|| type == Long.class || type == Double.class || type == Float.class
				|| type == java.math.BigDecimal.class||type == java.math.BigInteger.class) {
			if (o == null) {
				return 0;
			}
		} else if (type == java.util.Date.class || type == String.class) {
			if (o == null)
				return "";
		} else if (type == Object[].class) {
			if (o == null)
				return new Object[0];
		} else if (type == ArrayList.class || type == java.util.LinkedList.class) {
			if (o == null)
				return new ArrayList<>();
		} else if (type.isEnum()) {
			if (o == null) {
				return type.getEnumConstants()[0];
			}
		} else if (type != Object.class) {
			if (o == null) {
				try {
					o = type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			HashMap<String, Object> map = new HashMap<>();
			for (Field f : type.getDeclaredFields()) {
				Object object = null;
				try {
					f.setAccessible(true);
					object = f.get(o);
					if (object == null) {
						object = replaceObjectNullFields(object, f.getType());
					}
					map.put(f.getName(), object);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return map;
		}
		return o;
	}
}
