package br.com.hq;


import br.com.hq.model.Operation;
import br.com.hq.model.Summary;
import br.com.hq.model.util.Category;
import br.com.hq.utils.HttpUtil;
import br.com.hq.utils.JavaParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import java.util.*;

public class AssertTest {

	private final static String BACK_URL = "https://my-json-server.typicode.com/cairano/backend-test";
	private final static String DB_URL = BACK_URL + "/db";
	private final static String LOG = "br/com/hq/data.log";

	@Test
	public void test(){

		List<Operation> opList = new ArrayList<>();
		JsonArray jsArray;
		JavaParser parser = new JavaParser();

		// Backend server data consulting
		try {
			System.out.print("Buscando dados do servidor MOCK...");
			StringBuffer buffer = new StringBuffer("{\n" +
					"  \"pagamentos\": [\n" +
					"    {\n" +
					"      \"data\": \"11/jul\",\n" +
					"      \"descricao\": \"Auto Posto Shell\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-50,00\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"24/jun\",\n" +
					"      \"descricao\": \"Ofner\",\n" +
					"      \"moeda:\": \"R$\",\n" +
					"      \"valor\": \"-23,80\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"25/jun\",\n" +
					"      \"descricao\": \"Urbe Cafe\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-45,10\",\n" +
					"      \"categoria\": \"alimentação\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"30 / jun\",\n" +
					"      \"descricao\": \"Assai Atacadista\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-9,96\",\n" +
					"      \"categoria\": \"alimentação\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"30 / jun\",\n" +
					"      \"descricao\": \"Uber Do Brasil\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-4,16\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"02 / jul\",\n" +
					"      \"descricao\": \"Maremonti Campo Belo\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-12,32\",\n" +
					"      \"categoria\": \"alimentacao\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"03 / jul\",\n" +
					"      \"descricao\": \"Uber Do Brasil\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-7,63\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"05 / jul\",\n" +
					"      \"descricao\": \"Zelo\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-16,65\",\n" +
					"      \"categoria\": \"vestuario\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"05 / jul\",\n" +
					"      \"descricao\": \"Lucky Comercio\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-69,99\",\n" +
					"      \"categoria\": \"\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"06 / jul\",\n" +
					"      \"descricao\": \"Unidas Locadora\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-523,17\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"07 / jul\",\n" +
					"      \"descricao\": \"Farmacia Paraiso\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-12,50\",\n" +
					"      \"categoria\": \"higiene\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"07 / jul\",\n" +
					"      \"descricao\": \"Posto Vale Da Lua Ltda\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-180,06\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"09 / jul\",\n" +
					"      \"descricao\": \"Unidas Locadora\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-40,40\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"12 / jul\",\n" +
					"      \"descricao\": \"World Tennis\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"- 28,08\",\n" +
					"      \"categoria\": \"vestuario\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"15 / mai\",\n" +
					"      \"descricao\": \"Otica Max\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-55,00\",\n" +
					"      \"categoria\": \"vestuario\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"17 / mai\",\n" +
					"      \"descricao\": \"Lojas Renner\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-53,24\",\n" +
					"      \"categoria\": \"vestuario\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"17 / jun\",\n" +
					"      \"descricao\": \"Centauro\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-105,00\",\n" +
					"      \"categoria\": \"vestuario\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"09 / jul\",\n" +
					"      \"descricao\": \"Posto Vale Da Lua Ltda\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-168,48\",\n" +
					"      \"categoria\": \"transporte\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"13 / jul\",\n" +
					"      \"descricao\": \"Assai Atacadista\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"-140,91\",\n" +
					"      \"categoria\": \"alimentacao\"\n" +
					"    }\n" +
					"  ],\n" +
					"  \"recebimentos\": [\n" +
					"    {\n" +
					"      \"data\": \"10 / jul\",\n" +
					"      \"descricao\": \"Marcelo B.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"50,00\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"04 / jul\",\n" +
					"      \"descricao\": \"Antonio C.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"15,00\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"02 / jul\",\n" +
					"      \"descricao\": \"Luciano N.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"68,00\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"19 / abr\",\n" +
					"      \"descricao\": \"Marcelo B.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"62,00\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"07 / mai\",\n" +
					"      \"descricao\": \"Douglas S.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"45,00\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"20 / mai\",\n" +
					"      \"descricao\": \"Daniela S.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"6,00\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"data\": \"13 / jun\",\n" +
					"      \"descricao\": \"Renata B.\",\n" +
					"      \"moeda\": \"R$\",\n" +
					"      \"valor\": \"268,5\"\n" +
					"    }\n" +
					"  ]\n" +
					"}");
			jsArray = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("pagamentos");
			jsArray.addAll(new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("recebimentos"));

			opList.addAll(parser.jsonToList(jsArray));

		} catch (JsonSyntaxException e) {
			System.out.println("Erro ao converter Json para objeto Java!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// File reading
		try {
			URI uri = ClassLoader.getSystemClassLoader().getResource(LOG).toURI();
			System.out.println("Incluindo dados de:");
			System.out.println(uri.toString());

			Files.lines(Paths.get(uri)).forEach(line -> {
				if (!line.toLowerCase().contains("data")) {
					opList.add(parser.getOperation(line));
				}
			});
			System.out.println("Arquivo carregado com sucesso.");
		} catch (URISyntaxException e) {
			System.out.println("Erro no formato do endereço.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(" Arquivo não pode ser aberto.");
			e.printStackTrace();
		}

		// Exit app in case of void list
		if(opList == null || opList.size() == 0){
			System.out.println("Não constam transações. Programa será finalizado.");
			System.exit(-1);
		}

		Summary summary = new Summary(opList);

		System.out.println(summary.getGastosPorCategoria());

		System.out.println("Categoria de maior gasto: " + summary.getCategoriaMaiorGasto());

		System.out.println("Mês de maior gasto: " + summary.getMesMaiorGasto());

		System.out.println("Gasto total: " + summary.getGastoTotal());

		System.out.println("Receita total: " + summary.getReceitaTotal());

		System.out.println("Saldo: " + summary.getSaldo());

		Map<Category, Float> map = new HashMap<>();
		map.put(Category.transporte, Float.valueOf("-4.16"));
		map.put(Category.desconhecido, Float.valueOf("-69.99"));
		map.put(Category.diversao, Float.valueOf("-10.9"));
		map.put(Category.vestuario, Float.valueOf("-16.65"));
		map.put(Category.higiene, Float.valueOf("-12.5"));
		map.put(Category.hospedagem, Float.valueOf("-143.15"));
		map.put(Category.viagem, Float.valueOf("-430.49"));
		map.put(Category.alimentacao, Float.valueOf("-9.96"));

		Assert.assertEquals(map, summary.getGastosPorCategoria());
		Assert.assertEquals(Category.viagem, summary.getCategoriaMaiorGasto());
		Assert.assertEquals(Month.of(2), summary.getMesMaiorGasto());
		Assert.assertEquals(Float.valueOf("-4864.7993"), summary.getGastoTotal());
		Assert.assertEquals(Float.valueOf("1043.25"), summary.getReceitaTotal());
		Assert.assertEquals(Float.valueOf("-3821.5493"), summary.getSaldo());
	}
}
