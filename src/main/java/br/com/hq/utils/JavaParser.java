package br.com.hq.utils;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class JavaParser {
	
	public Float parseFloat(String json) {
		return Float.parseFloat(json.replace(",", ".").replace(" ", ""));
	}

	public MonthDay parseData(String data) {
		data = data.replace(" ", "").toLowerCase().replace("mai", "mar");
		return MonthDay.parse(data, DateTimeFormatter.ofPattern("dd/MM"));
	}
}
