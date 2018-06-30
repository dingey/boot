package com.d.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DateConverter implements Converter<String, Date> {
    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);

    private SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

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
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return null;
    }

}
