package com.d.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DateConverter implements Converter<String, Date> {
	SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

	@Override
	public Date convert(String source) {
		if (StringUtils.hasText(source)) {
			try {
				return dateTime.parse(source);
			} catch (ParseException ex) {
				try {
					return date.parse(source);
				} catch (ParseException ex1) {
					try {
						return time.parse(source);
					} catch (ParseException e) {
					}
				}
			}
		}
		return null;
	}

}
