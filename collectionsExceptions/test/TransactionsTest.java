import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionsTest {

    public static int INIT_SIZE = 5;
    public static final long ID = 1L;
    public static final String ACCOUNT_ID = "123456";
    public static final String SOURCE_BANK_ID = "2010";
    public static final BigDecimal AMOUNT = new BigDecimal("100");
    public static final Currency DEFAULT_CURRENCY = Currency.EUR;
    public static final String DEFAULT_DESCRIPTION = "default description";

    Transactions transactions;


    @Before
    public void setUp() {
        this.transactions = new Transactions();

        Random rand = new Random();
        // Testing data preparation
        for (int i = 0; i < INIT_SIZE; i++) {
            double randomAmount = rand.nextDouble();
            double amount = i % 2 == 0 ? randomAmount : -randomAmount;
            Transaction t = new Transaction(ID+i, ACCOUNT_ID, SOURCE_BANK_ID, BigDecimal.valueOf(amount), DEFAULT_CURRENCY, LocalDateTime.now().plusDays(i), DEFAULT_DESCRIPTION );
            transactions.add(t);
        }
        System.out.println("Transactions have been initialized: " + transactions.size());
        System.out.println(transactions);
    }

    @Test
    public void add() {
        int oldSize = transactions.size();

        Transaction expected = new Transaction(ID+oldSize, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
        transactions.add(expected);

        int actualSize = transactions.size();
        assertEquals(oldSize + 1, actualSize);

        Transaction actual = transactions.getLast();
        assertEquals(expected, actual);
    }

    @Test
    public void addNullTransaction() {
        try {
            transactions.add(null);
            fail("NullPointerException expected. Transaction is null.");
        } catch (NullPointerException _) {
        }
    }

    @Test
    public void addDuplicateTransaction() {
        Transaction duplicate = new Transaction(ID, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION);
        try {
            transactions.add(duplicate); // ID=1 už existuje z setUp()
            fail("DuplicateTransactionException expected.");
        } catch (DuplicateTransactionException _) {}
    }

    @Test
    public void removeAndReAdd() {
        Transaction t = transactions.getFirst();
        transactions.remove(t);
        assertEquals(INIT_SIZE - 1, transactions.size());

        transactions.add(t);
        assertEquals(INIT_SIZE, transactions.size());
    }

    @Test
    public void getByCurrency() {
        List<Transaction> eurTransactions = transactions.getByCurrency(DEFAULT_CURRENCY);
        eurTransactions.forEach(t -> assertEquals(DEFAULT_CURRENCY, t.getCurrency()));

        Transaction expected = new Transaction(transactions.size()+1, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, Currency.USD, LocalDateTime.now(), DEFAULT_DESCRIPTION );
        transactions.add(expected);

        List<Transaction> usdTransactions = transactions.getByCurrency(Currency.USD);
        assertEquals(1, usdTransactions.size());
        usdTransactions.forEach(t -> assertEquals(Currency.USD, t.getCurrency()));
    }

    @Test
    public void getByNullCurrency() {
        try {
            List<Transaction> t = transactions.getByCurrency(null);
            fail("NullPointerException expected. Currency is null.");
        } catch (IllegalArgumentException _) {}
    }

    @Test
    public void getByAccountId() {
        List<Transaction> accountIdTransactions = transactions.getByAccountId(ACCOUNT_ID);
        accountIdTransactions.forEach(t -> assertEquals(ACCOUNT_ID, t.getAccountId()));

        String expected = "111111";
        Transaction actual = new Transaction(transactions.size()+1, expected, SOURCE_BANK_ID, AMOUNT, Currency.USD, LocalDateTime.now(), DEFAULT_DESCRIPTION );
        transactions.add(actual);

        List<Transaction> actualTransactions = transactions.getByAccountId(expected);

        assertEquals(1, actualTransactions.size());
        actualTransactions.forEach(t -> assertEquals(expected, t.getAccountId()));
    }

    @Test
    public void getByBlankAccountId() {
        try {
        List<Transaction> accountIdTransactions = transactions.getByAccountId("");
        fail("IllegalArgumentException expected. AccountId is empty.");
        } catch (IllegalArgumentException _) {}
    }

    @Test
    public void getMoreExpensiveThan() {
        List<Transaction> actual = transactions.getMoreExpensiveThan(BigDecimal.TEN);
        actual.forEach(t -> assertTrue(t.getAmount().compareTo(BigDecimal.TEN) > 0));
    }

    @Test
    public void sortByAmountAsc() {
        transactions.sortByAmount(SortOrder.ASC);
        double first = transactions.getFirst().getAmount().doubleValue();

        for (int i = 1; i < INIT_SIZE; i++) {
            double current = transactions.get(i).getAmount().doubleValue();
            assertTrue(first <= current);
            first = current;
        }
    }

    @Test
    public void sortByAmountDesc() {
        transactions.sortByAmount(SortOrder.DESC);
        double first = transactions.getFirst().getAmount().doubleValue();

        for (int i = 1; i < INIT_SIZE; i++) {
            double current = transactions.get(i).getAmount().doubleValue();
            assertTrue(first >= current);
            first = current;
        }
    }

    @Test
    public void sortByDateAsc() {
        transactions.sortByDate(SortOrder.ASC);
        LocalDateTime first = transactions.getFirst().getDate();

        for (int i = 1; i < INIT_SIZE; i++) {
            LocalDateTime current =  transactions.get(i).getDate();
            assertTrue(first.isBefore(current));
        }
    }

    @Test
    public void sortByDateDesc() {
        transactions.sortByDate(SortOrder.DESC);
        LocalDateTime first = transactions.getFirst().getDate();
        for (int i = 1; i < INIT_SIZE; i++) {
            LocalDateTime current = transactions.get(i).getDate();
            assertTrue(first.isAfter(current));
        }
    }
}