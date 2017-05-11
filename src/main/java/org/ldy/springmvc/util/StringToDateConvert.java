package org.ldy.springmvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConvert implements Converter<String,Date> {
	private String datePattern;

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}


	public Date convert(String date) {
		// TODO Auto-generated method stub
		SimpleDateFormat dataFormat = new SimpleDateFormat(datePattern);
		try {
			return dataFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ÈÕÆÚ×ª»»Ê§°Ü");
			return null;
		}
	}

}
