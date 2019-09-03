package br.com.hq;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import br.com.hq.utils.HttpUtil;



public class BackendTest {

	private final static String BACK_URL = "https://my-json-server.typicode.com/cairano/backend-test";
	private final static String DB_URL = BACK_URL + "/db";
	// private final static String PAYMENT_URL = BACK_URL + "/pagamentos";
	// private final static String RECEIPTS_URL = BACK_URL + "/receitas";

	public static void main(String[] args) {
		
		
		HttpUtil http = new HttpUtil();

		JsonArray jsArrPaymts;
		JsonArray jsArrRecpts;

		try {
			
			StringBuffer buffer = new StringBuffer(http.sendGet(DB_URL));
			
			jsArrPaymts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("pagamentos");
			System.out.println(jsArrPaymts);
			
			jsArrRecpts = new JsonParser().parse(buffer.toString()).getAsJsonObject()
					.getAsJsonArray("recebimentos");
			System.out.println(jsArrRecpts);
			
		} catch (JsonSyntaxException e) {
			System.out.println("Error merging Json to Object!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}

}
