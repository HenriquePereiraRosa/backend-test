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
	
	public Float parseFloat(String str) {
		return Float.parseFloat(str.replace(",", ".").replace(" ", ""));
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
				.toUpperCase(), // TODO: check upperCase() need.
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
		MonthDay md = parseData(line.substring(0, 6), '-');

		Boolean descDone = false;
		StringBuffer descricao = new StringBuffer();
		Boolean valorDone = false;
		StringBuffer valor = new StringBuffer();
		StringBuffer categoria = new StringBuffer();

		for(int i = 6; i < line.length(); i++) {

			char charIndex = line.charAt(i);

			if((Character.isLetter(charIndex)  || (charIndex == '-') || (charIndex == '.') || (charIndex == ','))
					&& (charIndex != '-') && !descDone) { // descricao block
				descricao.append(charIndex);
			} else if((Character.isDigit(charIndex) || (charIndex == '-') || (charIndex == ',') || (charIndex == '.'))
					&& !valorDone) { // valor block
				descDone = true;
				valor.append(charIndex);
			} else if(Character.isLetter(charIndex)) {
				valorDone = true;
				categoria.append(charIndex);
			}
		}

		return new Operation(md, descricao.toString(),
				"R$", Float.parseFloat(valor.toString()),
				categoria.toString());
	}
}
