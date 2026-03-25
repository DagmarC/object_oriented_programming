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
