package br.com.hq;

import br.com.hq.model.Operation;
import br.com.hq.utils.HttpUtil;
import br.com.hq.utils.JavaParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Stream;

public class BackendTest {

	private final static String BACK_URL = "https://my-json-server.typicode.com/cairano/backend-test";
	private final static String DB_URL = BACK_URL + "/db";
	// private final static String PAYMENT_URL = BACK_URL + "/pagamentos";
	// private final static String RECEIPTS_URL = BACK_URL + "/receitas";

	public static void main(String[] args) {

        String root = Thread.currentThread()
                .getContextClassLoader()
                .getResource("")
                .getPath();
        String path = "file.properties"; //  root + "file.properties";
        Scanner scan = new Scanner(System.in);
        Properties prop = new Properties();
        StringBuffer fileName = new StringBuffer();
        List<Operation> fileList = new ArrayList<>();

        HttpUtil http = new HttpUtil();
        List<Operation> payList = new ArrayList<>();
        List<Operation> recvList = new ArrayList<>();
        JsonArray jsArrPaymts;
        JsonArray jsArrRecpts;

        JavaParser parser = new JavaParser();

        try {
        	prop.load(new FileInputStream(path));

			if(prop.getProperty("path") == null) {
				saveNewProperty(scan, prop, fileName, new FileWriter(path));
			} else{
				fileName.append(prop.getProperty("path"));
				System.out.println("Path carregado: ");
				System.out.println(fileName.toString());
				System.out.println("Deseja modificar o path do arquivo de entrada?");
				System.out.print("[s]im  ou [n]ão: ");
				if(scan.next().contains("s")) {
					saveNewProperty(scan, prop, fileName, new FileWriter(path));
				}
			}
			System.out.print("Aguarde processamento dos dados...");
        } catch (IOException ex) {
            System.out.println("Falha ao manipular o arquivo de propriedades.");
            System.out.println("Por favor, feche os arquivos e execute novamente.");
            ex.getMessage();
            ex.printStackTrace();
        }

		try {
			StringBuffer buffer = new StringBuffer(http.sendGet(DB_URL));
			jsArrPaymts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("pagamentos");

			jsArrRecpts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("recebimentos");

			payList = parser.jsonToList(jsArrPaymts);
			recvList = parser.jsonToList(jsArrRecpts);
			
		} catch (JsonSyntaxException e) {
			System.out.println("Erro ao converter Json para objeto Java!");
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


		try (Stream<String> stream = Files.lines(Paths.get(fileName.toString()))) {

            stream.forEach(line -> {
				System.out.println(line.substring(0, 6));
				if(!line.toLowerCase().contains("data")) {
					fileList.add(parser.getOperation(line));
				}
			});
            stream.close();

            // TODO: DEBUG
			fileList.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println(" Arquivo não pode ser aberto.");
			e.printStackTrace();
		}
	}

	private static void saveNewProperty(Scanner scan, Properties prop, StringBuffer fileName, FileWriter fileWriter) throws IOException {
		System.out.println("Digite o caminho do arquivo:");
		prop.setProperty("path", scan.next());
		fileName.append(prop.getProperty("path"));
		prop.store(fileWriter, null);
		fileWriter.close();
	}
}
