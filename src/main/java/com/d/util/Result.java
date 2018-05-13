package com.d.util;

public class Result {
	private Integer code = 0;
	private String message = null;
	private Object data = null;

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

	public static Result fail(Integer code, String message) {
		return new Result(code, message, null);
	}

	public static enum ErrCode {
		BAD_REQUEST(400, "错误的请求"), UNAUTHORIZED(401, "未授权"), FORBIDDEN(403, " 服务器拒绝请求"), NOT_FOUND(404,
				"请求的资源不存在"), METHOD_NOT_ALLOWED(405, "请求方法不允许"), REQUEST_TIME_OUT(408,
						"请求超时"), REQUEST_ENTITY_TOO_LARGE(413, "请求提交的实体数据过大"), UNSUPPORTED_MEDIA_TYPE(415,
								"不支持的媒体格式"), UNPROCESSABLE_ENTITY(422, "请求格式正确，但是由于含有语义错误，无法响应。"), LOCKED(423,
										"当前资源被锁定"), SERVER_ERR(500, "内部服务器错误"), SERVER_UNAVABLE(503, "服务不可用");

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
}
