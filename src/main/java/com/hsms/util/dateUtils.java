package com.hsms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class dateUtils {
	
	public static String getDateString(String format) {
		if(!StringUtils.hasLength(format)) {
			format = "yyyyMMddHHmmss";
		}
		DateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(new Date());
	}
	
}
