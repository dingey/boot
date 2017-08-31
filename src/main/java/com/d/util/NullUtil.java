package com.d.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
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
		if (type == java.util.Map.class || type == java.util.HashMap.class) {
			Map<Object, Object> m = (Map<Object, Object>) o;
			if (o == null) {
				return new HashMap<>();
			} else {
				for (Object key : m.keySet()) {
					Object v = m.get(key);
					if (v == null) {
						m.put(key, "");
					}else{
						m.put(key, replaceObjectNullFields(v, v.getClass()));
					}
				}
			}
			return m;
		} else if (type == java.lang.Byte.class) {
			if (o == null) {
				return Byte.valueOf("0");
			}
		} else if (type == java.lang.Short.class) {
			if (o == null) {
				return Short.valueOf("0");
			}
		} else if (type == java.lang.Integer.class) {
			if (o == null) {
				return Integer.valueOf("0");
			}
		} else if (type == java.lang.Long.class) {
			if (o == null) {
				return Long.valueOf("0");
			}
		} else if (type == java.lang.Double.class) {
			if (o == null) {
				return Double.valueOf("0");
			}
		} else if (type == java.lang.Float.class) {
			if (o == null) {
				return Float.valueOf("0");
			}
		} else if (type == java.math.BigDecimal.class) {
			if (o == null) {
				return BigDecimal.valueOf(0);
			}
		} else if (type == java.math.BigInteger.class) {
			if (o == null) {
				return BigInteger.valueOf(0);
			}
		} else if (type == java.util.Date.class || type == java.lang.String.class) {
			if (o == null)
				return "";
		} else if (type == Object[].class) {
			if (o == null)
				return new Object[0];
		} else if (type == java.util.ArrayList.class || type == java.util.LinkedList.class) {
			if (o == null)
				return new ArrayList<>();
		}else if(type.isEnum()){
			if (o == null) {
				return type.getEnumConstants()[0];
			}
		} else if (type != java.lang.Object.class) {			
			if (o == null) {
				try {
					o = type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			for (Field f : type.getDeclaredFields()) {
				Object object = null;
				try {
					f.setAccessible(true);
					object = f.get(o);
					if (object == null) {
						f.set(o, replaceObjectNullFields(object, f.getType()));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return o;
		}
		return o;
	}
}
