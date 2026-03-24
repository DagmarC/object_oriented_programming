package com.cervenkova.api;

import com.cervenkova.model.account.AccountInfo;
import com.cervenkova.model.account.BankApiResponse;
import com.cervenkova.model.enums.Currency;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MockFioApiClient implements BankApiClient {

    private final String token;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MockFioApiClient(String token) {
        this.token = token; // for testing purpose only, can be anything or Nan
    }

    @Override
    public BankApiResponse fetchAccountData(LocalDate from, LocalDate to) {
        String raw = loadJson();
        AccountInfo accountInfo = parseAccountInfo(raw);
        List<Map<String, Object>> transactions = parseTransactions(raw);
        return new BankApiResponse(accountInfo, transactions);
    }

    private String loadJson() {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("fio_response.json")) {
            if (is == null) throw new RuntimeException("fio_response.json not found in test resources");
            return new String(is.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load mock JSON", e);
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse account info", e);
        }
    }

    private List<Map<String, Object>> parseTransactions(String raw) {
        try {
            JsonNode transactions = objectMapper.readTree(raw)
                    .path("accountStatement")
                    .path("transactionList")
                    .path("transaction");

            List<Map<String, Object>> result = new ArrayList<>();

            for (JsonNode tx : transactions) {
                Map<String, Object> row = new HashMap<>();

                row.put("id",           getLong(tx,   "column22"));
                row.put("date",         getDate(tx,   "column0"));
                row.put("amount",       getDouble(tx, "column1"));
                row.put("currency",     getString(tx, "column14"));
                row.put("accountId",    getString(tx, "column2"));
                row.put("sourceBankId", getString(tx, "column3"));
                row.put("merchantName", getString(tx, "column10"));
                row.put("message",      getString(tx, "column7"));
                row.put("type",         getString(tx, "column8"));
                row.put("description",  getString(tx, "column25"));

                result.add(row);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse transactions", e);
        }
    }

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