package br.com.hq.utils;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class JavaParser {
	
	public Float parseFloat(String json) {
		return Float.parseFloat(json.replace(",", ".").replace(" ", ""));
	}

	public MonthDay parseData(String data) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd/MMM")
                .toFormatter(Locale.ENGLISH);
		
		return MonthDay.parse(data.replace(" ", "")
				.toLowerCase()
				.replace("fev", "feb")
				.replace("abr", "apr")
				.replace("mai", "may")
				.replace("ago", "aug")
				.replace("set", "sep")
				.replace("out", "oct")
				.replace("dez", "dec")
				.toUpperCase(),
				formatter);		
	}
}
