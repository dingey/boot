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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author d
 */
public class SqlProvider {
	static final HashMap<String, String> sqls = new HashMap<>();
	static final HashMap<String, List<Field>> modelFieldsMap = new HashMap<>();

	public String insert(Object bean) {
		return getCachedSql(bean, "insert", new Func<Object>() {
			@Override
			public String apply(Object t) {
				return getInsertSql(bean, false);
			}
		});
	}

	public String insertSelective(Object bean) {
		return getInsertSql(bean, true);
	}

	public String getInsertSql(Object bean, boolean selective) {
		String tableName = table(bean);
		StringBuilder insertSql = new StringBuilder();
		List<String> props = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		insertSql.append("INSERT INTO ").append(tableName).append("(");
		try {
			for (Field field : getCachedModelFields(bean.getClass())) {
				if (selective) {
					field.setAccessible(true);
					Object value = field.get(bean);
					if (value == null) {
						continue;
					}
				}
				if (field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(IgnoreInsert.class)) {
					continue;
				}
				columns.add(camel2Underline(field.getName()));
				props.add("#{" + field.getName() + "}");
			}
		} catch (Exception e) {
			new RuntimeException("get insert sql is exceptoin:" + e);
		}
		for (int i = 0; i < columns.size(); i++) {
			insertSql.append("`").append(columns.get(i)).append("`");
			if (i != columns.size() - 1)
				insertSql.append(",");
		}
		insertSql.append(")").append(" VALUES(");
		for (int i = 0; i < props.size(); i++) {
			insertSql.append(props.get(i));
			if (i != props.size() - 1)
				insertSql.append(",");
		}
		insertSql.append(")");
		return insertSql.toString();
	}

	public String update(Object bean) {
		return getCachedSql(bean, "update", new Func<Object>() {
			@Override
			public String apply(Object t) {
				return getUpdateSql(bean, false);
			}
		});
	}

	public String updateSelective(Object bean) {
		return getUpdateSql(bean, true);
	}

	public String getUpdateSql(Object bean, boolean selective) {
		String tableName = table(bean);
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("UPDATE ").append(tableName).append(" SET ");
		String id = "id";
		try {
			for (Field field : getCachedModelFields(bean.getClass())) {
				if (selective) {
					field.setAccessible(true);
					Object value = field.get(bean);
					if (value == null) {
						continue;
					}
				}
				if (field.isAnnotationPresent(Id.class)) {
					id = field.getName();
					continue;
				} else if (field.isAnnotationPresent(Transient.class)
						|| field.isAnnotationPresent(IgnoreUpdate.class)) {
					continue;
				}
				updateSql.append("`").append(camel2Underline(field.getName())).append("`=#{").append(field.getName())
						.append("},");
			}
		} catch (Exception e) {
			new RuntimeException("get update sql is exceptoin:" + e);
		}
		updateSql.deleteCharAt(updateSql.length() - 1);
		updateSql.append(" where ").append(camel2Underline(id)).append(" =#{").append(id).append("}");
		return updateSql.toString();
	}

	public String delete(Object bean) {
		return getCachedSql(bean, "delete", new Func<Object>() {
			@Override
			public String apply(Object t) {
				String tableName = table(bean);
				List<Field> fields = getCachedModelFields(bean.getClass());
				StringBuilder deleteSql = new StringBuilder();
				deleteSql.append(" DELETE FROM ").append(tableName).append(" WHERE ");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						if (field.isAnnotationPresent(Id.class)) {
							deleteSql.append(camel2Underline(field.getName())).append("=#{").append(field.getName())
									.append("}");
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

	public String deleteMark(Object bean) {
		return getCachedSql(bean, "deleteMark", new Func<Object>() {
			@Override
			public String apply(Object t) {
				String tableName = table(bean);
				List<Field> fields = getFields(bean.getClass());
				StringBuilder deleteSql = new StringBuilder();
				deleteSql.append(" UPDATE ").append(tableName).append(" SET ");
				String delete = "", id = "";
				try {
					for (Field field : fields) {
						if (field.isAnnotationPresent(DeleteMark.class)) {
							delete = camel2Underline(field.getName());
						} else if (field.isAnnotationPresent(Id.class)) {
							id = field.getName();
						}
					}
				} catch (Exception e) {
					new RuntimeException("get delete sql is exceptoin:" + e);
				}
				deleteSql.append(delete).append("=1 WHERE ").append(camel2Underline(id)).append("=#{").append(id)
						.append("}");
				return deleteSql.toString();
			}
		});
	}

	public String get(Object bean) {
		return getCachedSql(bean, "get", new Func<Object>() {
			@Override
			public String apply(Object t) {
				String tableName = table(bean);
				if (bean.getClass().isAnnotationPresent(Table.class)
						&& !bean.getClass().getAnnotation(Table.class).value().isEmpty()) {
					tableName = bean.getClass().getAnnotation(Table.class).value();
				}
				List<Field> fields = getFields(bean.getClass());
				StringBuilder getSql = new StringBuilder();
				getSql.append("SELECT * FROM ").append(tableName).append(" WHERE ");
				try {
					for (int i = 0; i < fields.size(); i++) {
						Field field = fields.get(i);
						if (field.isAnnotationPresent(Id.class)) {
							getSql.append(camel2Underline(field.getName())).append("=#{").append(field.getName())
									.append("} and");
						}
					}
					getSql.delete(getSql.toString().length() - 3, getSql.toString().length());
				} catch (Exception e) {
					new RuntimeException("get delete sql is exceptoin:" + e);
				}
				return getSql.toString();
			}
		});
	}

	public String listAll(Object bean) {
		return getCachedSql(bean, "listAll", new Func<Object>() {
			@Override
			public String apply(Object t) {
				String tableName = table(bean);
				StringBuilder getSql = new StringBuilder();
				getSql.append("SELECT * FROM ").append(tableName);
				return getSql.toString();
			}
		});
	}

	public String countAll(Object bean) {
		return getCachedSql(bean, "countAll", new Func<Object>() {
			@Override
			public String apply(Object t) {
				String tableName = table(bean);
				StringBuilder getSql = new StringBuilder();
				getSql.append("SELECT count(0) FROM ").append(tableName);
				return getSql.toString();
			}
		});
	}

	private static String getCachedSql(Object bean, String method, Func<Object> func) {
		String key = bean.getClass().getName() + "_" + method;
		if (sqls.containsKey(key)) {
			return sqls.get(key);
		} else {
			String apply = func.apply(bean);
			sqls.put(key, apply);
			return apply;
		}
	}

	public static List<Field> getCachedModelFields(Class<?> beanClass) {
		if (modelFieldsMap.containsKey(beanClass.getName())) {
			return modelFieldsMap.get(beanClass.getName());
		} else {
			List<Field> fields = getFields(beanClass);
			modelFieldsMap.put(beanClass.getName(), fields);
			return fields;
		}
	}

	public static List<Field> getFields(Class<?> beanClass) {
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

	public static String underline2Camel(String line, boolean smallCamel) {
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

	static String table(Object bean) {
		if (bean.getClass().isAnnotationPresent(Table.class)
				&& !bean.getClass().getAnnotation(Table.class).value().isEmpty()) {
			return bean.getClass().getAnnotation(Table.class).value();
		} else {
			return camel2Underline(bean.getClass().getSimpleName());
		}
	}

	public static String camel2Underline(String line) {
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

	@FunctionalInterface
	public interface Func<T> {
		String apply(T t);
	}

	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Table {
		String value();
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Id {
		boolean autoGenerated() default false;
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Transient {
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DeleteMark {
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface IgnoreInsert {
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface IgnoreUpdate {
	}
}
