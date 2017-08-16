package com.d.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author d
 */
public class SqlProvider {
	static HashMap<String, String> sqls = new HashMap<String, String>();

	public String insert(Object bean) {
		return getAndPut(bean, "insert", new Function<Object, String>() {
			@Override
			public String apply(Object t) {
				Class<?> beanClass = bean.getClass();
				String tableName = getTableName(beanClass);
				List<Field> fields = getFields(beanClass);
				StringBuilder insertSql = new StringBuilder();
				List<String> insertParas = new ArrayList<String>();
				List<String> insertParaNames = new ArrayList<String>();
				insertSql.append("INSERT INTO ").append(tableName).append("(");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						field.setAccessible(true);
						Object value = field.get(bean);
						if (((beanClass.isAnnotationPresent(IgnoreNull.class)
								&& beanClass.getAnnotation(IgnoreNull.class).value())
								|| (field.isAnnotationPresent(IgnoreNull.class)
										&& field.getAnnotation(IgnoreNull.class).value()))
								&& value == null || field.isAnnotationPresent(Transient.class)) {
							continue;
						}
						insertParaNames.add(getColumn(field));
						insertParas.add("#{" + field.getName() + "}");
					}
				} catch (Exception e) {
					new RuntimeException("get insert sql is exceptoin:" + e);
				}
				for (int i = 0; i < insertParaNames.size(); i++) {
					insertSql.append(insertParaNames.get(i));
					if (i != insertParaNames.size() - 1)
						insertSql.append(",");
				}
				insertSql.append(")").append(" VALUES(");
				for (int i = 0; i < insertParas.size(); i++) {
					insertSql.append(insertParas.get(i));
					if (i != insertParas.size() - 1)
						insertSql.append(",");
				}
				insertSql.append(")");
				return insertSql.toString();
			}
		});
	}

	public String update(Object bean) {
		return getAndPut(bean, "update", new Function<Object, String>() {
			@Override
			public String apply(Object t) {
				Class<?> beanClass = bean.getClass();
				String tableName = getTableName(beanClass);
				List<Field> fields = getFields(beanClass);
				StringBuilder updateSql = new StringBuilder();
				updateSql.append("UPDATE ").append(tableName).append(" SET ");
				String id = "id";
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						field.setAccessible(true);
						Object value = field.get(bean);
						if (((beanClass.isAnnotationPresent(IgnoreNull.class)
								&& beanClass.getAnnotation(IgnoreNull.class).value())
								|| (field.isAnnotationPresent(IgnoreNull.class)
										&& field.getAnnotation(IgnoreNull.class).value()))
								&& value == null || field.isAnnotationPresent(Transient.class)) {
							continue;
						} else if (field.isAnnotationPresent(Id.class)) {
							id = field.getName();
							continue;
						}
						updateSql.append(getColumn(field)).append("=#{").append(field.getName()).append("},");						
					}
				} catch (Exception e) {
					new RuntimeException("get update sql is exceptoin:" + e);
				}
				updateSql.deleteCharAt(updateSql.length()-1);
				updateSql.append(" where ").append(camel2Underline(id)).append(" =#{").append(id).append("}");
				return updateSql.toString();
			}
		});
	}

	public String realyDelete(Object bean) {
		return getAndPut(bean, "realyDelete", new Function<Object, String>() {
			@Override
			public String apply(Object t) {
				Class<?> beanClass = bean.getClass();
				String tableName = getTableName(beanClass);
				List<Field> fields = getFields(beanClass);
				StringBuilder deleteSql = new StringBuilder();
				deleteSql.append(" DELETE FROM ").append(tableName).append(" WHERE ");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						if (field.isAnnotationPresent(Id.class)) {
							deleteSql.append(getColumn(field)).append("=#{").append(field.getName()).append("}");
							break;
						}
					}
				} catch (Exception e) {
					new RuntimeException("get delete sql is exceptoin:" + e);
				}
				return deleteSql.toString();
			}
		});
	}

	public String delete(Object bean) {
		return getAndPut(bean, "delete", new Function<Object, String>() {
			@Override
			public String apply(Object t) {
				Class<?> beanClass = bean.getClass();
				String tableName = getTableName(beanClass);
				List<Field> fields = getFields(beanClass);
				StringBuilder deleteSql = new StringBuilder();
				deleteSql.append(" UPDATE ").append(tableName).append(" SET DEL_FLAG=1 WHERE ");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						if (field.isAnnotationPresent(Id.class)) {
							deleteSql.append(getColumn(field)).append("=#{").append(field.getName()).append("}");
							break;
						}
					}
				} catch (Exception e) {
					new RuntimeException("get delete sql is exceptoin:" + e);
				}
				return deleteSql.toString();
			}
		});
	}

	public String get(Object bean) {
		return getAndPut(bean, "get", new Function<Object, String>() {
			@Override
			public String apply(Object t) {
				Class<?> beanClass = bean.getClass();
				String tableName = getTableName(beanClass);
				List<Field> fields = getFields(beanClass);
				StringBuilder getSql = new StringBuilder();
				getSql.append("SELECT * FROM ").append(tableName).append(" WHERE ");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						if (field.isAnnotationPresent(Id.class)) {
							getSql.append(getColumn(field)).append("=#{").append(field.getName()).append("}");
							break;
						}
					}
				} catch (Exception e) {
					new RuntimeException("get delete sql is exceptoin:" + e);
				}
				return getSql.toString();
			}
		});
	}

	public String findList(Object bean) {
		return getAndPut(bean, "findList", new Function<Object, String>() {
			@Override
			public String apply(Object t) {
				Class<?> beanClass = bean.getClass();
				String tableName = getTableName(beanClass);
				StringBuilder getSql = new StringBuilder();
				getSql.append("SELECT * FROM ").append(tableName);
				return getSql.toString();
			}
		});
	}

	static String getAndPut(Object bean, String method, Function<Object, String> func) {
		String key = bean.getClass().getName() + "_" + method;
		if (!bean.getClass().isAnnotationPresent(IgnoreNull.class) && sqls.containsKey(key)) {
			return sqls.get(key);
		} else {
			String apply = func.apply(bean);
			if (!bean.getClass().isAnnotationPresent(IgnoreNull.class)) {
				sqls.put(key, apply);
			}
			return apply;
		}
	}

	String getTableName(Class<?> beanClass) {
		return camel2Underline(beanClass.getSimpleName());
	}

	String getColumn(Field field) {
		return camel2Underline(field.getName());
	}

	private List<Field> getFields(Class<?> beanClass) {
		Class<?> clazz = beanClass;
		List<Field> fields = new ArrayList<>();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				for (Field f : clazz.getDeclaredFields()) {
					int modifiers = f.getModifiers();
					if (!Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isNative(modifiers)
							&& !Modifier.isTransient(modifiers)) {
						fields.add(f);
					}
				}
			} catch (Exception e) {
			}
		}
		return fields;
	}

	/**
	 * 下划线转驼峰法
	 * 
	 * @param line
	 *            源字符串
	 * @param smallCamel
	 *            大小驼峰,是否为小驼峰
	 * @return 转换后的字符串
	 */
	static String underline2Camel(String line, boolean smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
					: Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰法转下划线
	 * 
	 * @param line
	 *            源字符串
	 * @return 转换后的字符串
	 */
	static String camel2Underline(String line) {
		if (line == null || "".equals(line)) {
			return "";
		}
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toLowerCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}

	@Target({ ElementType.FIELD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface IgnoreNull {
		boolean value() default true;
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Id {
		boolean value() default true;
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Transient {
		boolean value() default true;
	}
}
