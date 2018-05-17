package com.ssxt.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class DateConvert implements Converter<String, Date> {

	public Date convert(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(stringDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}