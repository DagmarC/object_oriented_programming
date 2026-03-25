package com.cervenkova.api;

import com.cervenkova.model.account.Account;
import com.cervenkova.model.account.BankApiResponse;
import com.cervenkova.model.enums.Currency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map;


/**
 * Raw response from Fio.
 */
public class FioApiClient implements BankApiClient {

    private static final String BASE_URL =
            "https://fioapi.fio.cz/v1/rest/periods/%s/%s/%s/transactions.json";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String token;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public FioApiClient(String token) {
        if (token == null || token.isBlank()) throw new IllegalArgumentException("Token is null or blank");
        this.token = token.trim();
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public BankApiResponse fetchAccountData(LocalDate from, LocalDate to) {
        String raw = fetch(from, to);
        try {
            JsonNode root = objectMapper.readTree(raw); // Len RAZ
            JsonNode accountStatement = root.path("accountStatement");

            Account account = parseAccountInfo(accountStatement);
            List<Map<String, Object>> transactions = parseTransactions(accountStatement);

            return new BankApiResponse(account, transactions);
        } catch (Exception e) {
            throw new RuntimeException("ERROR: Parsing JSON tree.", e);
        }
    }

    private String fetch(LocalDate from, LocalDate to) {
        String url = String.format(BASE_URL, token, from.format(DATE_FORMAT), to.format(DATE_FORMAT));
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("ERROR: Failed : HTTP error code : " + response.statusCode());
            }
            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("ERROR: Fetching data from FioAPI: " + e);
        }
    }

    private List<Map<String, Object>> parseTransactions(JsonNode accountStatement) {

        JsonNode transactionsNode = accountStatement.path("transactionList").path("transaction");
        List<Map<String, Object>> transactionList = new ArrayList<>();

        if (transactionsNode.isArray()) {
            for (JsonNode tx : transactionsNode) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", getString(tx, "column22"));
                row.put("date", getString(tx, "column0"));
                row.put("amount", getString(tx, "column1"));
                row.put("currency", getString(tx, "column14"));
                row.put("accountId", getString(tx, "column2"));
                row.put("sourceBankId", getString(tx, "column3"));
                row.put("merchantName", getString(tx, "column10"));
                row.put("message", getString(tx, "column7"));
                row.put("type", getString(tx, "column8"));
                row.put("description", getString(tx, "column25"));

                transactionList.add(row);
            }
        }
        return transactionList;
    }

    private Account parseAccountInfo(JsonNode accountStatement) {
        JsonNode info = accountStatement.path("info");
        return new Account(
                info.path("accountId").asText(),
                info.path("bankId").asText(),
                info.path("iban").asText(),
                Currency.valueOf(info.path("currency").asText().toUpperCase()),
                new BigDecimal(info.path("closingBalance").asText())
        );
    }

    // --- Helper methods ---
    private String getString(JsonNode tx, String col) {
        JsonNode node = tx.path(col).path("value");
        return node.isNull() || node.isMissingNode() ? null : node.asText().trim();
    }
}
