<h1 align="center">BACKEND TEST</h1>

<p align="center">
 <img src="https://i.imgur.com/kNWsCCC.gif?1" alt="Backend Test"></a>
</p>


<div align="center">
  
  [![Status](https://img.shields.io/badge/status-active-success.svg)]()
  [![GitHub Issues](https://img.shields.io/github/issues/henriquepereirarosa/backend-test.svg)](https://github.com/HenriquePereiraRosa/backend-test/issues)
  [![GitHub Pull Requests](https://img.shields.io/github/issues-pr/henriquepereirarosa/backend-test.svg)](https://github.com/HenriquePereiraRosa/backend-test/pulls)
  [![License](https://img.shields.io/badge/license-CC0-blue.svg)](http://creativecommons.org/publicdomain/zero/1.0/)
    
</div>

---

<p align = "center">💡 See the explanation below.</p>


## Table of Contents

- [Conceito de desenvolvimento deste projeto](#concept)
- [Primeira fase](#first)
- [Segunda fase](#second)
- [Terceira fase](#third)
- [Testes jUnit](#tests)
- [Execução do arquivo .jar](#exe)


## Conceito de desenvolvimento deste projeto <a name = "concept"></a>

- Utilizando os padrões de projeto java 8 e através do problema proposto foram dividas 3 taferas principais: 
 1. [Aquisição dos dados (Backend server e arquivo .log).](#first)
 2. [Merge dos dados.](#second)
 3. [Processamento e classificação dos dados.](#third)

 	E por fim foram concluídos os testes que foram implementados durante a implementação do projeto utilizando técnicas TDD.(#tests)


## Primeira fase<a name = "first"></a>

**Conexão com o servidor e conversão do Json:**

- A classe **HttpURLConnection** foi a responsável pelas requisições HTTP ao servidor.
- Para produção da string foi utiliza a classe **StringBuffer** por possui uma melhor performance.
- A biblioteca **Gson** Efetuou a conversão de JSON para ArrayListLink.

- A classe **JavaParser (referenciada como parser)** foi criada com o objetivo de converser JsonArray para ArrayList<Operation>.
````
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
````


## Segunda fase<a name = "second"></a>

**Leitura do arquivo e junção com os dados retidos do servidor:**

- A nova classe **Files** do java 8 foi a responsável pela leitura do arquivo que foi adquirido como resource ao projeto.
- Para produção da string foi utiliza a classe **StringBuffer** por possui uma melhor performance.
- A biblioteca **Gson** Efetuou a conversão de JSON para ArrayListLink.

- A classe ``**JavaParser (referenciada como parser)**`` foi criada com o objetivo de converser JsonArray para ArrayList<Operation>. 

````
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
````
 
 - houve também a inclusasão de uma saída de emergência caso haja algum erro com o arquivo & o servidor esteja indisponível.
 ````
 		// Exit app in case of void list
        if(opList == null || opList.size() == 0){
            System.out.println("Não constam transações. Programa será finalizado.");
            System.exit(-1);
        }
````

## Terceira fase<a name = "third"></a>

**Interface de apresentação dos dados**

- A ordenação da lista foi realizada com a classe ``**Comparator**`` do ``java 8``, e a apresentação com ``Method::reference``: 

``
		// Dados ordenados por data
        System.out.println();
        System.out.println(" - Extrato ordenado por DATA:");
        opList.sort(Comparator.comparing(Operation::getData));
        opList.forEach(System.out::println);
``


**Categoria e mês de maior gasto**

- A categoria e mês de maior gasto foram adquiridas com a ``ordenação de um Map de gastos``.
- Foi necessária a criaçao da classe ``Summary`` com método de manupulação e criaçao de um resumo da junção dos dados.

 ````
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
        System.out.println("Lista gastos por Mês:");
        System.out.println(summary.getGastosPorMesToString());

        // Mês de maior gasto
        System.out.println("Mês com maior gasto: "
                + summary.getMesMaiorGasto()
                + " (" + summary.getGastosPorMes()
                    .get(summary.getMesMaiorGasto())
                + ")");

        System.out.println();
````

- Por fim os demais itens obtidos através do objeto ``Summary``:
````
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
        System.out.println("Lista gastos por Mês:");
        System.out.println(summary.getGastosPorMesToString());

        // Mês de maior gasto
        System.out.println("Mês com maior gasto: "
                + summary.getMesMaiorGasto()
                + " (" + summary.getGastosPorMes()
                    .get(summary.getMesMaiorGasto())
                + ")");

        System.out.println();
 `````


## Testes no Projeto projeto <a name = "tests"></a>

- Por ser uma classe "Util", a classe ``JavaParser``, contém um especificidade de testes e por esse motivo foi testada em ``test.br.com.hq.utils.JavaParserTest``:
````
	@Test
	public void test() {
		assertEquals(140.00, parser.parseFloat("140,00"), 0.0001);
		assertEquals(140.00, parser.parseFloat(" 140, 00"), 0.0001);
		assertEquals(140.00, parser.parseFloat(" 140,00 "), 0.0001);
		assertEquals(-140.00, parser.parseFloat("-140,00"), 0.0001);
		assertEquals(140.00, parser.parseFloat("14 0,00 "), 0.0001);
		assertEquals(140.00, parser.parseFloat("14 0,00"), 0.0001);
		assertEquals(140.00, parser.parseFloat("1 40,00"), 0.0001);
		assertEquals(1040.00, parser.parseFloat("1. 040,00"), 0.0001);
		assertEquals(1040.00, parser.parseFloat("1 040,00"), 0.0001);
		assertEquals(-1040.00, parser.parseFloat("-1 040,00"), 0.0001);
		assertEquals(-1.00000000376832E14, parser.parseFloat("-100 .00 0..0 00.000.040,00"), 0.01);
		

		assertEquals(MonthDay.of(07, 31), parser.parseData("31/jul", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData("31/ jul", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31/ jul ", '/'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31_ jul ", '_'));
		assertEquals(MonthDay.of(07, 31), parser.parseData(" 31. jul ", '.'));
	}
```` 

- A classe Main e Summary seriam melhor testdas juntas assim foi criaod um Json "fake" para o teste: 

 ````
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
		map.put(Category.alimentacao, Float.valueOf("-537.31"));
		map.put(Category.viagem, Float.valueOf("-430.49"));
		map.put(Category.diversao, Float.valueOf("-262.22998"));
		map.put(Category.vestuario, Float.valueOf("-317.92"));
		map.put(Category.hospedagem, Float.valueOf("-1912.19"));
		map.put(Category.higiene, Float.valueOf("-40.57"));
		map.put(Category.desconhecido, Float.valueOf("-283.25"));
		map.put(Category.transporte, Float.valueOf("-1080.84"));

		Assert.assertEquals(map, summary.getGastosPorCategoria());
		Assert.assertEquals(Category.hospedagem, summary.getCategoriaMaiorGasto());
		Assert.assertEquals(Month.of(5), summary.getMesMaiorGasto());
		Assert.assertEquals(Float.valueOf("-4864.7993"), summary.getGastoTotal(), 0.01);
		Assert.assertEquals(Float.valueOf("1043.25"), summary.getReceitaTotal(), 0.01);
		Assert.assertEquals(Float.valueOf("-3821.5493"), summary.getSaldo(), 0.01);
 ````


## Executando o projeto <a name = "exe"></a>

1. O projeto foi desenvolvido na IDE Intellij e o ``jar`` do preojeto pode ser compilado, ou baixado na seçao [RELEASE](https://github.com/HenriquePereiraRosa/backend-test/releases/tag/v0.0.1-RELEASE) do peojeto.

2. Através do prompt de comando (cmd) navege até a pasta que o contém o arquivo .jar.

3. Execute com o comando "java -jar backend-test.jar".  


Exemplo de saída:
````
Buscando dados do servidor...
Sending 'GET' request to URL : https://my-json-server.typicode.com/cairano/backend-test/db
Response Code : 200
Incluindo dados de do arquivo .log
Arquivo carregado com sucesso.

 - Extrato ordenado por DATA:
 - data=--02-17, descricao='TAM SITE', moeda='R$', valor=-430.49, categoria=viagem
 - data=--03-21, descricao='INGRESSO.COM', moeda='R$', valor=-159.53, categoria=diversao
 - data=--03-21, descricao='NESPRESSO', moeda='R$', valor=-55.9, categoria=desconhecido
 - data=--03-22, descricao='IBIS PARQUE OLIMPICO', moeda='R$', valor=-143.15, categoria=hospedagem
 - data=--03-27, descricao='PONTO FRIOCOM', moeda='R$', valor=-213.26, categoria=desconhecido
 - data=--04-19, descricao='Marcelo B.', moeda='R$', valor=62.0, categoria=desconhecido
 - data=--04-26, descricao='LOJAS RENNER FL', moeda='R$', valor=-59.95, categoria=vestuario
 - data=--05-07, descricao='Douglas S.', moeda='R$', valor=45.0, categoria=desconhecido
 - data=--05-15, descricao='Otica Max', moeda='R$', valor=-55.0, categoria=vestuario
 - data=--05-17, descricao='Lojas Renner', moeda='R$', valor=-53.24, categoria=vestuario
 - data=--05-20, descricao='Daniela S.', moeda='R$', valor=6.0, categoria=desconhecido
 - data=--05-21, descricao='Camila Souza', moeda='R$', valor=35.0, categoria=desconhecido
 - data=--05-21, descricao='Antonio Coutinho', moeda='R$', valor=120.0, categoria=desconhecido
 - data=--05-24, descricao='Evino', moeda='R$', valor=-62.43, categoria=desconhecido
 - data=--05-24, descricao='Uber Do Brasil Tecnolog', moeda='R$', valor=-6.66, categoria=transporte
 - data=--05-25, descricao='COMETA TIETE', moeda='R$', valor=-28.28, categoria=transporte
 - data=--05-25, descricao='Droga Raia', moeda='R$', valor=-14.09, categoria=higiene
 - data=--05-25, descricao='Uber Do Brasil Tecnolog', moeda='R$', valor=-8.65, categoria=transporte
 - data=--05-25, descricao='UATT', moeda='R$', valor=-79.9, categoria=alimentacao
 - data=--05-26, descricao='SONDA SUPERMERCADO', moeda='R$', valor=-41.89, categoria=alimentacao
 - data=--05-26, descricao='Uber Do Brasil Tecnolog', moeda='R$', valor=-9.39, categoria=transporte
 - data=--05-27, descricao='EBANX AIRBNB', moeda='R$', valor=-1430.44, categoria=hospedagem
 - data=--05-27, descricao='Droga Raia', moeda='R$', valor=-13.98, categoria=higiene
 - data=--05-27, descricao='ITUNES.COMBILL', moeda='R$', valor=-74.9, categoria=diversao
 - data=--05-29, descricao='Hirota', moeda='R$', valor=-13.0, categoria=alimentacao
 - data=--05-30, descricao='HARU LANCHONETE', moeda='R$', valor=-75.9, categoria=alimentacao
 - data=--06-01, descricao='Uber Do Brasil Tecnolog', moeda='R$', valor=-7.04, categoria=transporte
 - data=--06-02, descricao='Uber Do Brasil Tecnolog', moeda='R$', valor=-6.09, categoria=transporte
 - data=--06-02, descricao='Jose Mota', moeda='R$', valor=35.0, categoria=desconhecido
 - data=--06-02, descricao='RECARGAPAY BILH UNICO', moeda='R$', valor=-10.0, categoria=transporte
 - data=--06-03, descricao='ITUNES.COMBILL', moeda='R$', valor=-16.9, categoria=diversao
 - data=--06-05, descricao='Uber Do Brasil Tecnolog', moeda='R$', valor=-7.03, categoria=transporte
 - data=--06-12, descricao='ITUNES.COMBILL', moeda='R$', valor=-10.9, categoria=diversao
 - data=--06-13, descricao='Renata B.', moeda='R$', valor=268.5, categoria=desconhecido
 - data=--06-17, descricao='Centauro', moeda='R$', valor=-105.0, categoria=vestuario
 - data=--06-20, descricao='EBANX AIRBNB', moeda='R$', valor=-338.6, categoria=hospedagem
 - data=--06-20, descricao='EBANX AIRBNB', moeda='R$', valor=338.75, categoria=hospedagem
 - data=--06-24, descricao='Ofner', moeda='null', valor=-23.8, categoria=transporte
 - data=--06-25, descricao='Urbe Cafe', moeda='R$', valor=-45.1, categoria=desconhecido
 - data=--06-30, descricao='Assai Atacadista', moeda='R$', valor=-9.96, categoria=desconhecido
 - data=--06-30, descricao='Uber Do Brasil', moeda='R$', valor=-4.16, categoria=transporte
 - data=--07-02, descricao='Maremonti Campo Belo', moeda='R$', valor=-12.32, categoria=alimentacao
 - data=--07-02, descricao='Luciano N.', moeda='R$', valor=68.0, categoria=desconhecido
 - data=--07-03, descricao='Uber Do Brasil', moeda='R$', valor=-7.63, categoria=transporte
 - data=--07-04, descricao='Antonio C.', moeda='R$', valor=15.0, categoria=desconhecido
 - data=--07-05, descricao='Zelo', moeda='R$', valor=-16.65, categoria=vestuario
 - data=--07-05, descricao='Lucky Comercio', moeda='R$', valor=-69.99, categoria=desconhecido
 - data=--07-06, descricao='Unidas Locadora', moeda='R$', valor=-523.17, categoria=transporte
 - data=--07-07, descricao='Farmacia Paraiso', moeda='R$', valor=-12.5, categoria=higiene
 - data=--07-07, descricao='Posto Vale Da Lua Ltda', moeda='R$', valor=-180.06, categoria=transporte
 - data=--07-09, descricao='Unidas Locadora', moeda='R$', valor=-40.4, categoria=transporte
 - data=--07-09, descricao='Posto Vale Da Lua Ltda', moeda='R$', valor=-168.48, categoria=transporte
 - data=--07-10, descricao='Marcelo B.', moeda='R$', valor=50.0, categoria=desconhecido
 - data=--07-11, descricao='Auto Posto Shell', moeda='R$', valor=-50.0, categoria=transporte
 - data=--07-12, descricao='World Tennis', moeda='R$', valor=-28.08, categoria=vestuario
 - data=--07-13, descricao='Assai Atacadista', moeda='R$', valor=-140.91, categoria=alimentacao

----====== RESUMO DAS TRANSAÇOES =====----
Gastos por categoria:
 - hospedagem: -1912.19
 - transporte: -1080.84
 - desconhecido: -456.63998
 - viagem: -430.49
 - alimentacao: -363.92
 - vestuario: -317.91998
 - diversao: -262.22998
 - higiene: -40.57

Categoria com maior gasto: hospedagem (-1912.19)

Lista gastos por mês:
 - MAY: -1967.75
 - JULY: -1250.1901
 - JUNE: -584.57996
 - MARCH: -571.83997
 - FEBRUARY: -430.49
 - APRIL: -59.95

Mês com maior gasto: MAY (-1967.75)

Gasto total: -4864.8
Receita total: 1043.25
Saldo: -3821.5498

````

<a rel="dct:publisher"
     href="https://github.com/henriquepereirarosa/">
    <span property="dct:title">Henrique P. Rosa</span></a>
</p>
