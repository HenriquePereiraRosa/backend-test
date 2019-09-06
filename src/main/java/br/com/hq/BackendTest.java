package br.com.hq;

import br.com.hq.model.Operation;
import br.com.hq.model.OperatorStr;
import br.com.hq.utils.HttpUtil;
import br.com.hq.utils.JavaParser;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BackendTest {

	private final static String BACK_URL = "https://my-json-server.typicode.com/cairano/backend-test";
	private final static String DB_URL = BACK_URL + "/db";
	// private final static String PAYMENT_URL = BACK_URL + "/pagamentos";
	// private final static String RECEIPTS_URL = BACK_URL + "/receitas";

	public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o caminho do arquivo: ");
		String fileName = scan.next();
		List<Operation> fileList = new ArrayList<>();

		HttpUtil http = new HttpUtil();
		List<Operation> payList = new ArrayList<>();
		List<Operation> recvList = new ArrayList<>();
		JsonArray jsArrPaymts;
		JsonArray jsArrRecpts;

		JavaParser parser = new JavaParser();

		try {
			
			StringBuffer buffer = new StringBuffer(http.sendGet(DB_URL));
			jsArrPaymts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("pagamentos");

			jsArrRecpts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("recebimentos");

			payList = parser.jsonToList(jsArrPaymts);
			recvList = parser.jsonToList(jsArrRecpts);
			
		} catch (JsonSyntaxException e) {
			System.out.println("Error merging Json to Object!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO: to remove (DEBUG)
		System.out.println("++++++++++++++++++++++++++++++++++++++");
		payList.forEach(p -> {
			System.out.print(p.getDescricao());
			System.out.print(" | ");
			System.out.print(p.getValor());
			System.out.print(" | ");
			System.out.println(p.getData());
		});

		// TODO: to remove (DEBUG)
		System.out.println("++++++++++++++++++++++++++++++++++++++");
		recvList.forEach(p -> {
			System.out.print(p.getDescricao());
			System.out.print(" | ");
			System.out.print(p.getValor());
			System.out.print(" | ");
			System.out.println(p.getData());
		});


		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(line -> {
				System.out.println(line.substring(0, 6));
				if(!line.toLowerCase().contains("data")) {
					fileList.add(parser.getOperation(line));
				}
			});
            stream.close();

			fileList.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println(" Arquivo não pode ser aberto.");
			e.printStackTrace();
		}
	}
}
