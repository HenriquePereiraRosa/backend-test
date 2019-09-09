package br.com.hq;

import br.com.hq.model.Operation;
import br.com.hq.utils.HttpUtil;
import br.com.hq.utils.JavaParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private final static String BACK_URL = "https://my-json-server.typicode.com/cairano/backend-test";
    private final static String DB_URL = BACK_URL + "/db";
    private final static String LOG = "br/com/hq/data.log";

    public static void main(String[] args) {

        String fileName;
        HttpUtil http = new HttpUtil();
        List<Operation> opList = new ArrayList<>();
        JsonArray jsArray;
        JavaParser parser = new JavaParser();

        System.out.print("Buscando dados do servidor...");
        try {
            StringBuffer buffer = new StringBuffer(http.sendGet(DB_URL));
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

        System.out.println("Incluindo dados do arquivo .log...");
        try {
            URI uri = ClassLoader.getSystemClassLoader().getResource(LOG).toURI();
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

        // Dados ordenados por data
        System.out.println(" - Arquivos ordenados por DATA:");
        opList.sort(Comparator.comparing(br.com.hq.model.Operation::getData));
        opList.forEach(System.out::println);

        // Gastos por categoria


        // Categoria de maior gasto

        // Mês de maior gasto

        // Gasto Total

        // Receita total

        // Saldo

    }
}