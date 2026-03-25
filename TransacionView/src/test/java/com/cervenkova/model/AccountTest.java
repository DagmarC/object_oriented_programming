package com.cervenkova.model;

import com.cervenkova.model.account.Account;
import com.cervenkova.model.enums.Category;
import com.cervenkova.model.enums.Currency;
import com.cervenkova.model.transaction.IncomingTransaction;
import com.cervenkova.model.transaction.SpendingTransaction;
import com.cervenkova.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(
                "111222",
                "2010",
                "SK1112222010",
                Currency.EUR,
                new BigDecimal("1000.00")
        );
    }

    @Test
    void constructor_validParameters_createsAccountAndInitializesFields() {
        String expectedAccountId = "999888";
        String expectedBankId = "0900";
        String expectedIban = "SK9998880900";
        Currency expectedCurrency = Currency.USD;
        BigDecimal expectedBalance = new BigDecimal("500.50");

        // Act
        Account newAccount = new Account(
                expectedAccountId,
                expectedBankId,
                expectedIban,
                expectedCurrency,
                expectedBalance
        );

        assertEquals(expectedAccountId, newAccount.getAccountId());
        assertEquals(expectedBankId, newAccount.getBankId());
        assertEquals(expectedIban, newAccount.getIban());
        assertEquals(expectedCurrency, newAccount.getCurrency());
        assertEquals(expectedBalance, newAccount.getBalance());

        assertNotNull(newAccount.getTransactions());
        assertTrue(newAccount.getTransactions().isEmpty());
    }

    @Test
    void constructor_nullAccountId_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Account(null, "222", "SK111", Currency.EUR, BigDecimal.ZERO)
        );
        assertEquals("AccountId cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_nullIban_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Account("111", "222", null, Currency.EUR, BigDecimal.ZERO)
        );
        assertEquals("Iban cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_nullCurrency_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Account("111", "222", "SK111", null, BigDecimal.ZERO)
        );
        assertEquals("Currency cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_nullBalance_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Account("111", "222", "SK111", Currency.EUR, null)
        );
        assertEquals("Balance cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_nullBankId_optional() {
        Account a = new Account("111", null, "111", Currency.EUR, BigDecimal.ZERO);
        assertNotNull(a);
        assertNull(a.getBankId());
    }

    @Test
    void createIncomingTransaction_valid() {
        assertNotNull(createIncomingTransaction(new BigDecimal(12)), "Transaction created");
    }

    @Test
    void createSpendingTransaction_valid() {
        assertNotNull(createSpendingTransaction(new BigDecimal(-12)), "Transaction created");
    }

    @Test
    void createSpendingTransaction_invalid() {
        assertThrows(IllegalArgumentException.class, () -> createSpendingTransaction(new BigDecimal(100)));
    }

    @Test
    void createIncomingTransaction_invalid() {
        assertThrows(IllegalArgumentException.class, () -> createIncomingTransaction(new BigDecimal(-100)));
    }

    @Test
    void addIncomingTransaction_valid() {
        Transaction t1 = createIncomingTransaction(new BigDecimal(100));

        account.addTransaction(t1);
        assertEquals(1, account.getTransactions().size());
        assertEquals(t1, account.getTransactions().getFirst());
    }

    @Test
    void addSpendingTransaction_valid() {
        Transaction t1 = createSpendingTransaction(new BigDecimal(-100));

        account.addTransaction(t1);
        assertEquals(1, account.getTransactions().size());
        assertEquals(t1, account.getTransactions().getFirst());
    }

    @Test
    void addTransaction_invalid() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.addTransaction(null)
        );

        assertEquals("Transaction cannot be null", exception.getMessage());
    }

    @Test
    void addTransaction_unmodifiableList() {
        Transaction t1 = createIncomingTransaction(new BigDecimal(100));
        account.addTransaction(t1);
        List<Transaction> transactions = account.getTransactions();
        // Only Account class can add transaction via addTransaction() method
        assertThrows(UnsupportedOperationException.class, () -> transactions.add(t1));
    }

    @Test
    void totalSpending_calculateSum() {
        account.addTransaction(createSpendingTransaction(new BigDecimal(-100)));
        account.addTransaction(createSpendingTransaction(new BigDecimal(-50)));
        account.addTransaction(createSpendingTransaction(new BigDecimal(-20)));

        BigDecimal total = account.totalSpending();
        assertEquals(new BigDecimal(170), total); // abs() is called in the method
    }

    @Test
    void totalIncome_calculateSum() {
        account.addTransaction(createIncomingTransaction(new BigDecimal(100)));
        account.addTransaction(createIncomingTransaction(new BigDecimal(50)));
        account.addTransaction(createIncomingTransaction(new BigDecimal(20)));

        BigDecimal total = account.totalIncome();
        assertEquals(new BigDecimal(170), total); // abs() is called in the method
    }

    @Test
    void totalSpending_withNoTransactions_returnsZero() {
        assertEquals(BigDecimal.ZERO, account.totalSpending());
    }

    @Test
    void totalIncome_withNoTransactions_returnsZero() {
        assertEquals(BigDecimal.ZERO, account.totalIncome());
    }

    private Transaction createIncomingTransaction(BigDecimal amount) {
        return new IncomingTransaction(
                1L,
                LocalDateTime.now(),
                amount,
                account.getCurrency(),
                account.getAccountId(),
                account.getBankId(),
                "RETURN",
                "message",
                "via BANK",
                "test incoming transaction",
                Category.INCOME
        );
    }

    private Transaction createSpendingTransaction(BigDecimal amount) {
        return new SpendingTransaction(
                1L,
                LocalDateTime.now(),
                amount,
                account.getCurrency(),
                account.getAccountId(),
                account.getBankId(),
                "IKEA",
                "message",
                "via BANK",
                "test spending transaction",
                Category.OTHER
        );
    }
}
