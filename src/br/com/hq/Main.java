package br.com.hq;

import br.com.hq.model.Operation;
import br.com.hq.model.Summary;
import br.com.hq.utils.HttpUtil;
import br.com.hq.utils.JavaParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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
    //private final static String LOG = "data.log";

    public static void main(String[] args) {

        HttpUtil http = new HttpUtil();
        List<Operation> opList = new ArrayList<>();
        JsonArray jsArray;
        JavaParser parser = new JavaParser();

        // Backend server data consulting
        try {
            System.out.print("Buscando dados do servidor...");
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

        // File reading
        //            URI uri = ClassLoader.getSystemClassLoader().getResource(LOG).toURI();
//            System.out.println("Incluindo dados de:");
//            System.out.println(uri.toString());
//
//            Files.lines(Paths.get(uri)).forEach(line -> {
//                if (!line.toLowerCase().contains("data")) {
//                    opList.add(parser.getOperation(line));
//                }
//            });
        Stream stream = new BufferedReader(
                new InputStreamReader(
                        ClassLoader.getSystemClassLoader().getResourceAsStream(LOG))).lines();
        System.out.println("Incluindo dados de do arquivo .log");

        stream.forEach(line -> {
            if (!line.toString().toLowerCase().contains("data")) {
                opList.add(parser.getOperation(line.toString()));
            }});
        System.out.println("Arquivo carregado com sucesso.");

        // Exit app in case of void list
        if(opList == null || opList.size() == 0){
            System.out.println("Não constam transações. Programa será finalizado.");
            System.exit(-1);
        }

        // =============== Start point of Data Presentation ===============

        // Dados ordenados por data
        System.out.println();
        System.out.println(" - Extrato ordenado por DATA:");
        opList.sort(Comparator.comparing(Operation::getData));
        opList.forEach(System.out::println);

        Summary summary = new Summary(opList);
        System.out.println();
        System.out.println("----====== RESUMO DAS TRANSAÇOES =====----");

        // Gastos por categoria
        System.out.println("Gastos por categoria:");
        System.out.println(summary.getGastosPorCategoriaToString());

        // Categoria de maior gasto
        System.out.println("Categoria com maior gasto: "
                + summary.getCategoriaMaiorGasto()
                + " (" + summary.getGastosPorCategoria()
                    .get(summary.getCategoriaMaiorGasto())
                + ")");

        // Optional: Month list
        System.out.println();
        System.out.println("Lista gastos por mês:");
        System.out.println(summary.getGastosPorMesToString());

        // Mês de maior gasto
        System.out.println("Mês com maior gasto: "
                + summary.getMesMaiorGasto()
                + " (" + summary.getGastosPorMes()
                    .get(summary.getMesMaiorGasto())
                + ")");

        System.out.println();

        // Gasto Total
        System.out.println("Gasto total: " + summary.getGastoTotal());

        // Receita total
        System.out.println("Receita total: " + summary.getReceitaTotal());

        // Saldo
        System.out.println("Saldo: " + summary.getSaldo());
    }
}