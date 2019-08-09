package com.d.config;

import com.di.kit.AESUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.Charset;

/**
 * @author ding
 * @since 2019/8/9
 */
public class DecryptionMessageConverter extends AbstractHttpMessageConverter {
	public DecryptionMessageConverter() {
		super(new MediaType("application", "json", Charset.forName("UTF-8")));
	}

	@Override
	protected boolean supports(Class aClass) {
		boolean b = aClass.isAnnotationPresent(Decryptable.class);
		return b;
	}

	@Override
	protected Object readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
		String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
		String decrypt = AESUtil.decrypt(temp);
		if (StringUtils.isEmpty(decrypt)) {
			throw new RuntimeException("请求参数错误");
		}
		return new ObjectMapper().readValue(decrypt, aClass);
	}

	@Override
	protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
		httpOutputMessage.getBody().write(new ObjectMapper().writeValueAsString(o).getBytes());
	}

	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Decryptable {
	}
}
