package com.cervenkova.api;

import com.cervenkova.model.account.AccountInfo;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map;

// HTTP: data access, talks to Fio API (URLs, tokens, raw data)

/**
 * FIO RAW TRANSACTION DETAIL
 *
 *
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

        AccountInfo accountInfo = parseAccountInfo(raw);
        List<Map<String, Object>> transactions = parseTransactions(raw);

        return new BankApiResponse(accountInfo, transactions);
    }

    private String fetch(LocalDate from, LocalDate to) {
        String url = String.format(BASE_URL, token, from, to);
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("ERROR: Failed : HTTP error code : " + response.statusCode());
            }
            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("ERROR: Fetching data from FioAPI: "+ e);
        }
    }

    private List<Map<String, Object>> parseTransactions(String raw) {

        try {
           JsonNode transactions = objectMapper.readTree(raw)
            .path("accountStatement")
                   .path("transactionList")
                   .path("transaction");

           List<Map<String, Object>> transactionList = new ArrayList<>();

           for (JsonNode tx : transactions) {
               Map<String, Object> row = new HashMap<>();
               row.put("id",           getLong(tx,   "column22")); // ID pohybu
               row.put("date",         getDate(tx,   "column0"));  // Datum
               row.put("amount",       getDouble(tx, "column1"));  // Objem
               row.put("currency",     getString(tx, "column14")); // Měna
               row.put("accountId",    getString(tx, "column2"));  // Protiúčet
               row.put("sourceBankId", getString(tx, "column3"));  // Kód banky
               row.put("merchantName", getString(tx, "column10")); // Název protiúčtu
               row.put("message",      getString(tx, "column7"));  // Uživatelská identifikace
               row.put("type",         getString(tx, "column8"));  // Typ platby
               row.put("description",  getString(tx, "column25")); // Komentář

               transactionList.add(row);
           }
            return transactionList;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse transactions", e);
        }
    }

    private AccountInfo parseAccountInfo(String raw) {
        try {
            JsonNode info = objectMapper.readTree(raw)
                    .path("accountStatement")
                    .path("info");

            return new AccountInfo(
                    info.path("accountId").asText(),
                    info.path("bankId").asText(),
                    info.path("iban").asText(),
                    Currency.valueOf(info.path("currency").asText()),
                    BigDecimal.valueOf(info.path("closingBalance").asDouble())
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse account info", e);
        }
    }

    // --- Helper methods ---

    private String getString(JsonNode tx, String col) {
        JsonNode node = tx.path(col).path("value");
        return node.isNull() || node.isMissingNode() ? null : node.asText().trim();
    }

    private double getDouble(JsonNode tx, String col) {
        JsonNode node = tx.path(col).path("value");
        return node.isNull() || node.isMissingNode() ? 0.0 : node.asDouble();
    }

    private long getLong(JsonNode tx, String col) {
        JsonNode node = tx.path(col).path("value");
        return node.isNull() || node.isMissingNode() ? 0L : node.asLong();
    }

    private LocalDateTime getDate(JsonNode tx, String col) {
        String val = getString(tx, col);
        if (val == null) return null;
        return LocalDate.parse(val.substring(0, 10), DATE_FORMAT).atStartOfDay();
    }

}
