package com.d.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("返回信息")
@JsonSerialize(using = Result.ResultSerializer.class)
@SuppressWarnings("all")
public class Result {
	@ApiModelProperty("编码：0成功；其他失败；")
	@JsonInclude(Include.ALWAYS)
	private Integer code = 0;

	@ApiModelProperty("失败时返回的说明")
	@JsonInclude(Include.NON_EMPTY)
	private String message;

	@ApiModelProperty("成功时返回的数据")
	@JsonInclude(Include.NON_NULL)
	private Object data;

	@ApiModelProperty(value = "中间map变量", hidden = true)
	@JsonInclude(Include.NON_ABSENT)
	private Map<String, Object> map;

	public void setCode(Integer code) {
		this.code = code;
	}

	public Result(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@SuppressWarnings("unchecked")
	public <T> T getData() {
		return (T) data;
	}

	public static Result success() {
		return success(null);
	}

	public static Result success(Object data) {
		return new Result(0, null, data);
	}

	public static Result fail(String message) {
		return new Result(1, message, null);
	}

	public static Result fail(ErrCode errCode) {
		return new Result(errCode.getCode(), errCode.getName(), null);
	}

	@Deprecated
	public static Result fail(Integer code, String message) {
		return new Result(code, message, null);
	}

	public Result put(String key, Object value) {
		if (map == null) {
			map = new LinkedHashMap<>();
		}
		map.put(key, value);
		return this;
	}

	public static Result success(String key, Object value) {
		return success().put(key, value);
	}

	public enum ErrCode {
		BAD_REQUEST(400, "错误的请求"), //
		UNAUTHORIZED(401, "未授权"), //
		FORBIDDEN(403, " 服务器拒绝请求"), //
		NOT_FOUND(404, "请求的资源不存在"), //
		METHOD_NOT_ALLOWED(405, "请求方法不允许"), //
		REQUEST_TIME_OUT(408, "请求超时"), //
		REQUEST_ENTITY_TOO_LARGE(413, "请求提交的实体数据过大"), //
		UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体格式"), //
		UNPROCESSABLE_ENTITY(422, "请求格式正确，但是由于含有语义错误，无法响应。"), //
		LOCKED(423, "当前资源被锁定"), //
		SERVER_ERR(500, "内部服务器错误"), //
		SERVER_UNAVABLE(503, "服务不可用");//

		private int code;
		private String name;

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		ErrCode(int code, String name) {
			this.code = code;
			this.name = name;
		}
	}

	public static class ResultSerializer extends JsonSerializer<Result> {
		@Override
		public void serialize(Result r, JsonGenerator j, SerializerProvider serializerProvider) throws IOException {
			j.writeStartObject();
			if (r.code != null) {
				j.writeNumberField("code", r.code);
			}
			if (r.data != null) {
				if (r.map != null) {
					r.map.remove("data");
				}
				j.writeObjectField("data", r.data);
			}
			if (StringUtils.hasText(r.message)) {
				if (r.map != null) {
					r.map.remove("message");
				}
				j.writeStringField("message", r.message);
			}
			if (r.map != null) {
				for (String key : r.map.keySet()) {
					j.writeObjectField(key, r.map.get(key));
				}
			}
			j.writeEndObject();
		}
	}
}
