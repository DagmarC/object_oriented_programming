package com.cervenkova.api;

import com.cervenkova.model.account.BankApiResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockFioApiClientTest {

    private BankApiClient client;
    private BankApiResponse response;

    @BeforeAll
    public void setUp() {
        System.out.println("MockFioApiClientTest::setUp");

        String token = System.getenv("FIO_TOKEN"); // To be able to call real FioApiClient to see what the real response returns
        client = new MockFioApiClient(token);

        response = client.fetchAccountData(
                LocalDate.now().minusMonths(1),
                LocalDate.now()
        );
        System.out.println("Response: " + response);
    }

    @Test
    void fetchAccountData_returnsAccountId() {
        assertEquals("2000000000", response.account().getAccountId());
    }

    @Test
    void fetchAccountData_returnsBankId() {
        assertEquals("2010", response.account().getBankId());
    }

    @Test
    void fetchAccountData_returnsCurrency() {
        assertEquals("CZK", response.account().getCurrency().name());
    }

    @Test
    void fetchAccountData_returnsBalance() {
        assertEquals(0, response.account().getBalance()
                .compareTo(new BigDecimal("1234.56")));
    }

    @Test
    void fetchAccountData_returnsIban() {
        assertEquals("CZ1000000000002000000000", response.account().getIban());
    }

    // --- Transaction list ---

    @Test
    void fetchAccountData_returnsTransactionList() {
        assertFalse(response.rawTransactions().isEmpty());
        assertEquals(1, response.rawTransactions().size());
    }

    @Test
    void fetchAccountData_returnsTransactionId() {
        long id = (long) getResponseValueByKey("id");
        assertEquals(100000000L, id);
    }

    @Test
    void fetchAccountData_returnsTransactionAmount() {
        double amount = (double) getResponseValueByKey("amount");
        assertEquals(-2769.00, amount);
    }

    @Test
    void fetchAccountData_returnsTransactionCurrency() {
        assertEquals("CZK", getResponseValueByKey("currency").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionAccountId() {
        assertEquals("2222233333", getResponseValueByKey("accountId").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionSourceBankId() {
        assertEquals("2010", getResponseValueByKey("sourceBankId").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionMerchantName() {
        assertEquals("IKEA CR", getResponseValueByKey("merchantName").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionMessage() {
        assertEquals("Nákup IKEA", getResponseValueByKey("message").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionType() {
        assertEquals("Platba kartou", getResponseValueByKey("type").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionDescription() {
        assertEquals("Můj komentář", getResponseValueByKey("description").toString());
    }

    @Test
    void fetchAccountData_returnsTransactionDate() {
        LocalDateTime date = (LocalDateTime) getResponseValueByKey("date");
        assertNotNull(date);
        assertEquals(2023, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    // --- Helper method ---
    private Object getResponseValueByKey(String key) {
        return response.rawTransactions().get(0).get(key);
    }

}
