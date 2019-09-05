package br.com.hq.utils;

import br.com.hq.model.Operation;
import br.com.hq.model.OperatorStr;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JavaParser {
	
	public Float parseFloat(String json) {
		return Float.parseFloat(json.replace(",", ".").replace(" ", ""));
	}

	public MonthDay parseData(String data, char separator) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd" + separator + "MMM")
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

	public List<Operation> jsonToList(JsonArray jsArr) {

		List<Operation> list = new ArrayList<>();

		for (JsonElement elem : jsArr) {
			OperatorStr payStr = new Gson().fromJson(elem, OperatorStr.class);
			Operation pay = new Operation(parseData(payStr.getData(), '/'),
					payStr.getDescricao(), payStr.getMoeda(),
					parseFloat(payStr.getValor()), payStr.getCategoria());

			// TODO: DEBUG
			System.out.println(elem);
			list.add(pay);
		}
		return list;
	}

	public Operation getOperation(String line) {
		return null;
	}
}
