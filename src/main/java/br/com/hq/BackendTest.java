package br.com.hq;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import br.com.hq.model.Payment;
import br.com.hq.model.PaymentString;
import br.com.hq.utils.HttpUtil;
import br.com.hq.utils.JavaParser;



public class BackendTest {

	private final static String BACK_URL = "https://my-json-server.typicode.com/cairano/backend-test";
	private final static String DB_URL = BACK_URL + "/db";
	// private final static String PAYMENT_URL = BACK_URL + "/pagamentos";
	// private final static String RECEIPTS_URL = BACK_URL + "/receitas";

	public static void main(String[] args) {		
		
		HttpUtil http = new HttpUtil();
		
		List<Payment> payList = new ArrayList<Payment>();

		JsonArray jsArrPaymts;
		JsonArray jsArrRecpts;

		try {
			
			StringBuffer buffer = new StringBuffer(http.sendGet(DB_URL));
			
			jsArrPaymts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("pagamentos");
			
			// TODO: to remove (DEBUG)
			System.out.println(jsArrPaymts);
			
			jsArrRecpts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("recebimentos");
			
			// TODO: to remove (DEBUG)
			System.out.println(jsArrRecpts);
			
			for(JsonElement elem : jsArrPaymts) {
				System.out.println(elem);
				
				PaymentString payStr = new Gson().fromJson(elem, PaymentString.class);
				JavaParser parser = new JavaParser();
				Payment pay = new Payment(parser.parseData(payStr.getData()),
						payStr.getDescricao(), payStr.getMoeda(),
						parser.parseFloat(payStr.getValor()), payStr.getCategoria());
				
				payList.add(pay);
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
			
		} catch (JsonSyntaxException e) {
			System.out.println("Error merging Json to Object!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}

}
