# 💰 Currency Converter Microservice 🔄

## Resumo 📝

Este é um micro-serviço construído com Spring Boot que tem como finalidade realizar a conversão de moedas. Ele se integra com a API [Exchange Rates Data](https://apilayer.com/marketplace/exchangerates_data-api?utm_source=apilayermarketplace&utm_medium=featured) da apilayer.com para obter as taxas de câmbio mais recentes e fornecer conversões precisas entre diferentes moedas.

## Tecnologias Utilizadas 🛠️

* **Gradle:** [Official Gradle documentation](https://docs.gradle.org/)
* **Spring Boot Gradle Plugin:** [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)
* **Spring Boot:** (Consultar documentação base do Spring Boot)
* **Spring Web:** [Spring Framework Reference Documentation - Web on Servlet Stack](https://docs.spring.io/spring-framework/reference/web.html)
* **Spring Data JPA:** [Spring Data JPA - Reference Documentation](https://spring.io/projects/spring-data-jpa)
* **Spring Boot Actuator:** [Spring Boot Actuator - Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
* **Flyway Migration:** [Flyway by Redgate - Documentation](https://flywaydb.org/documentation/)
* **H2 Database:** [H2 Database Engine - Documentation](https://www.h2database.com/html/index.html)
* **Zalando Logbook:** [Zalando Logbook - Documentation](https://zalando.github.io/logbook/)

## Como Executar Localmente com IntelliJ IDEA 💡

1.  **Importar o Projeto:**
    * Abra o IntelliJ IDEA.
    * Clique em `File` -> `Open...` e selecione o diretório raiz do seu projeto.
    * O IntelliJ IDEA detectará automaticamente o arquivo `build.gradle.kts` (ou `build.gradle`) e configurará o projeto como um projeto Gradle.

2.  **Construir o Projeto (Opcional):**
    * No painel `Gradle` (geralmente à direita), localize a tarefa `build` dentro do seu projeto e execute-a (duplo clique). Isso irá baixar as dependências e construir o projeto. O IntelliJ IDEA geralmente faz isso automaticamente.

3.  **Executar a Aplicação Spring Boot:**
    * No painel `Project` (geralmente à esquerda), navegue até a classe principal da sua aplicação Spring Boot (geralmente anotada com `@SpringBootApplication`).
    * Clique com o botão direito sobre a classe principal.
    * Selecione `Run '<sua_classe_principal>'`.
    * O IntelliJ IDEA irá compilar e iniciar o seu micro-serviço Spring Boot. Você poderá acompanhar os logs na janela `Run`.
    * Certifique-se de que não haja outras aplicações rodando na mesma porta (geralmente 8080 por padrão, mas pode ser configurada em `application.properties` ou `application.yml`).

## Como Chamar os Endpoints da API 📞

Você pode usar ferramentas como Postman 📬, Insomnia 😴, SoapUI 🧼 ou `curl` 💻 para interagir com os endpoints do seu micro-serviço. Abaixo estão exemplos de como chamar os endpoints definidos no seu `CurrencyConverterController`.

### Endpoint: `/currency-converter/exchange` (POST) 📤

Este endpoint realiza a conversão de moeda.

**Ferramenta: Postman/Insomnia**

* **Método:** `POST`
* **URL:** `http://localhost:8080/currency-converter/exchange` (assumindo que sua aplicação está rodando na porta 8080)
* **Body (JSON):**

    ```json
    {
      "userId": 123
      "currencyFrom": "USD",
      "currencyTo": "BRL",
      "amount": 100.00
    }
    ```

* **Headers:** Certifique-se de que o header `Content-Type` esteja definido como `application/json`.

**Ferramenta: `curl`**

```bash
curl -X POST \
  http://localhost:8080/currency-converter/exchange \
  -H 'Content-Type: application/json' \
  -d '{
    "userId": 123
    "currencyFrom": "USD",
    "currencyTo": "BRL",
    "amount": 100.00
  }'
```

**Resposta (Sucesso - Código 200) ✅:**

```json
{
    "id": 1,
    "userId": 123,
    "sourceCurrency": "USD",
    "sourceAmount": 100.0,
    "targetCurrency": "BRL",
    "targetAmount": 565.66,
    "exchangeRate": 5.656604,
    "registrationDate": "2025-05-03 12:20:15"
  }
```

### Endpoint: `/currency-converter/transactions/{userId}` (GET) 📥

Este endpoint consulta todas as transações de conversão realizadas por um usuário específico. Substitua `{userId}` pelo ID do usuário desejado.

**Ferramenta: Postman/Insomnia**

* **Método:** `GET`
* **URL:** `http://localhost:8080/currency-converter/transactions/123` (substitua `123` pelo ID do usuário)

**Ferramenta: `curl`**

```bash
curl -X GET http://localhost:8080/currency-converter/transactions/123
```

**Resposta (Sucesso - Código 200) ✅:**

```json
[
  {
    "id": 1,
    "userId": 123,
    "sourceCurrency": "USD",
    "sourceAmount": 100.0,
    "targetCurrency": "BRL",
    "targetAmount": 565.66,
    "exchangeRate": 5.656604,
    "registrationDate": "2025-05-03 11:20:15"
  },
  {
    "id": 1,
    "userId": 123,
    "sourceCurrency": "USD",
    "sourceAmount": 100.0,
    "targetCurrency": "BRL",
    "targetAmount": 565.66,
    "exchangeRate": 5.656604,
    "registrationDate": "2025-05-03 12:20:15"
  }
]
```

## Acessando a Documentação da API com Swagger UI 🌐

Este projeto utiliza o Swagger para gerar uma documentação interativa da API. Você pode acessar essa documentação através de um navegador web utilizando a seguinte URL (assumindo que sua aplicação esteja rodando na porta padrão 8080): `http://localhost:8080/swagger-ui/index.html`

---
<p align="left">
  <strong>Tarcísio de Lima</strong><br>
  Java | Kotlin | Backend Software Developer<br>
  <a href="mailto:tarcisio.lima.amorim@outlook.com">tarcisio.lima.amorim@outlook.com</a><br>
  Bitbucket: <a href="https://bitbucket.org/lima_t/" target="_blank">https://bitbucket.org/lima_t/</a>
</p>