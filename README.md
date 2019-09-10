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
  - [Novice Technical Writers](https://www.writethedocs.org/guide/#new-to-caring-about-documentation)
  - [Experienced Technical Writers](https://www.writethedocs.org/guide/#experienced-documentarian)
  - [API Documentation](https://www.writethedocs.org/guide/#api-documentation)
  - [Adding badges](https://github.com/badges/shields/blob/master/README.md#examples)
  - [Tools](https://www.writethedocs.org/guide/#tools-of-the-trade)
- [Technical Writer Programs](#programs)
- [Awesome Technical Writing Sources](#sources)
- [Get Feedback](#feedback)
- [Acknowledgements](#acknowledgements)


## Conceito de desenvolvimento deste projeto <a name = "concept"></a>

- Utilizando os padrões de projeto java 8 e através do problema proposto foram dividas 3 taferas principais: 
 1. Aquisição dos dados (Backend server e arquivo .log).(#first)
 2. Merge dos dados.(#second)
 3. Processamento e classificação dos dados.(#third)

 	E por fim foram concluídos os testes que foram implementados durante a implementação do projeto utilizando técnicas TDD.(#tests)


## Primeira fase<a name = "first"></a>

**Conexão com o servidor e conversão do Json:**

- A classe **HttpURLCOnnection** foi a responsável pelas requisições HTTP ao servidor.
- Para produção da string foi utiliza a classe **StringBuffer** por possui uma melhor performance.
- A biblioteca **Gson** Efetuou a conversão de JSON para ArrayListLink.

- A classe **JavaParser (referencia. como parser)** foi criada com o objetivo de converser JsonArray para ArrayList<Operation>.
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

- A classe **JavaParser (referencia. como parser)** foi criada com o objetivo de converser JsonArray para ArrayList<Operation>.  
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

- Don't assume prior knowledge about the topic. If you want to appeal to a large audience, then you are going to have people with very diverse backgrounds
- Don't use idioms. Write using more formal terms that are well defined. This makes it easier for non-native English speakers and for translations to be written
- Don't clutter explanations with overly detailed examples
- Don't use terms that are offensive to any group. There will never be a good reason to


## Templates <a name = "templates"></a>

- [README](/en/README_TEMPLATES)
- [Pull Request](/en/PULL_REQUEST_TEMPLATE.md)
- [Issues](/en/ISSUE_TEMPLATES)
- [Contributing](/en/CONTRIBUTING.md)
- [Code of Conduct](/en/CODE_OF_CONDUCT.md)
- [Coding Guidelines](/en/CODING_GUIDELINES.md)
- [Codebase Structure](/en/CODEBASE_STRUCTURE.md)
- [Changelog](/en/CHANGELOG.md)
- [TODO](/en/TODO.md)
  

## Technical Writing Programs <a name = "programs"></a>

1. [Google Season of Docs](https://developers.google.com/season-of-docs/)
2. [A List of Open Source Projects with Volunteer Documentation Opportunities](https://www.reddit.com/r/technicalwriting/comments/800a9a/a_list_of_open_source_projects_with_volunteer/)


## Awesome Technical Writing Sources <a name = "sources"></a>

1. [r/technicalwriting](https://www.reddit.com/r/technicalwriting/)
2. [My Tech Writing Process](https://amrutaranade.com/2018/03/07/my-writing-process/) - Amruta Ranade
3. [Developer to Technical Writer](https://www.reddit.com/r/technicalwriting/comments/a1x6c8/) - r/technicalwriting
4. [awesome-github-templates](https://github.com/devspace/awesome-github-templates) - devspace
5. [makeareadme](https://www.makeareadme.com/) - dguo
6. [What nobody tells you about documentation](https://www.divio.com/blog/documentation/) - Daniele Procida
7. [3 Essential Components of Great Documentation](https://dev.to/eli/3-essential-components-of-great-documentation-2cih) - Eli B
8. [Inspiring techies to become great writers](http://cameronshorter.blogspot.com/2019/02/inspiring-techies-to-become-great.html) - Cameron Shorter
9. [Technical Documentation Writing Principles](http://cameronshorter.blogspot.com/2018/06/technical-documentation-writing.html) - Cameron Shorter
10. [Building Our Documentation Site on platformOS — Part 2: Content Production and Layouts](https://www.platformos.com/blog/post/blog/building-our-documentation-site-on-platformos-part-2-content-production-and-layouts) - Diana Lakato
11. [Google Developer Documentation Style Guide](https://developers.google.com/style/) - Google
12. [README Maturity Model](https://github.com/LappleApple/feedmereadmes/blob/master/README-maturity-model.md) - LappleApple
13. [Markdown Style Guide](http://www.cirosantilli.com/markdown-style-guide/) - Ciro Santilli


## Get Feedback <a name = "feedback"></a>

- [feedmereadmes](https://github.com/LappleApple/feedmereadmes) - Free README editing + feedback to make your open-source projects grow. See the README maturity model to help you keep going
- [maintainer.io](https://maintainer.io/) - Free README standardization and feedback if you click on 'Book an audit'


## Acknowledgements <a name = "acknowledgements"></a>

1. [Documenting your projects on GitHub](https://guides.github.com/features/wikis/) - GitHub Guides
2. [documentation-handbook](https://github.com/jamiebuilds/documentation-handbook) - jamiebuilds
3. [Documentation Guide](https://www.writethedocs.org/guide/) - Write the Docs

  <a rel="dct:publisher"
     href="https://github.com/henriquepereirarosa/">
    <span property="dct:title">Henrique P. Rosa</span></a>
</p>
