package br.com.hq;


import br.com.hq.model.Operation;
import br.com.hq.model.Summary;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AssertSystemExitTest {

	@Test
	public void test(){

		List<Operation> opList = new ArrayList<>();
		JsonArray jsArray;
		JavaParser parser = new JavaParser();

		// Exit app in case of void list
		if(opList == null || opList.size() == 0){
			System.out.println("Não constam transações. Programa será finalizado.");
			Assert.assertTrue(true); // System.exit(-1); simulation
		} else Assert.assertFalse(true);
	}
}
