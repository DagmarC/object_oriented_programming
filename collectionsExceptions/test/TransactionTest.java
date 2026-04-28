import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionTest {

    // Default parameters for Testing Transaction
    public static final long ID = 1L;
    public static final String ACCOUNT_ID = "123456";
    public static final String SOURCE_BANK_ID = "2010";
    public static final BigDecimal AMOUNT = new BigDecimal("100");
    public static final Currency DEFAULT_CURRENCY = Currency.EUR;
    public static final String DEFAULT_DESCRIPTION = "default description";

    // Note: I could have many tests to cover the class, to test every parameter of the constructor. Bank usually has some mandatory fields that needs to be always present, checked for null and tested properly.
    // I have chosen Id, AccountId, SourceBankId, date, currency and amount as mandatory,
    // Then we have optional fields, where we can test whether it won't throw error at all

    @Test
    public void createTransaction() {
        Transaction actual = new Transaction(ID, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );

        assertEquals(ID, actual.getId());
        assertEquals(ACCOUNT_ID, actual.getAccountId());
        assertEquals(SOURCE_BANK_ID, actual.getSourceBankId());
        assertEquals(AMOUNT, actual.getAmount());
        assertEquals(DEFAULT_CURRENCY, actual.getCurrency());
        assertEquals(DEFAULT_DESCRIPTION, actual.getDescription());
    }

    @Test
    public void invalidId() {
        try {
            long invalidId = -1L;
            Transaction actual = new Transaction(invalidId, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception - invalid id");
        } catch (IllegalArgumentException _) {}
    }

    @Test
    public void nullAccountId() {
        try {
            new Transaction(ID, null, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception -  invalid accountId[null]");
        } catch (NullPointerException _) {}
    }


    @Test
    public void blankAccountId() {
        try {
            new Transaction(ID, "\n", SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception -  invalid accountId[blank]");
        } catch (NullPointerException _) {}
    }

    @Test
    public void accountIdTrimsWhiteSpace() {
        try {
            String accountId = "123457   \t \n";
            String expected = "123457";

            Transaction t = new Transaction(ID, accountId, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );

            assertNotNull(t);
            assertEquals(expected, t.getAccountId());

        } catch (Exception _) {
            fail("Creating Transaction should not throw an exception - accountId[white_space]");
        }
    }

    @Test
    public void wrongFormatAccountId() {
        try {
            String accountId = "12345ds5 ";

            new Transaction(ID, accountId, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception -  invalid accountId[wrong format]");
        } catch (IllegalArgumentException _) {}
    }

    @Test
    public void nullSourceBankId() {
        try {
            Transaction actual = new Transaction(ID, ACCOUNT_ID, null, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception -  invalid sourceBankId[null]");
        } catch (NullPointerException _) {}
    }

    @Test
    public void blankSourceBankId() {
        try {
            Transaction actual = new Transaction(ID, ACCOUNT_ID, "\t", AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception -  invalid sourceBankId[blank]");
        } catch (NullPointerException _) {}
    }

    @Test
    public void wrongFormatSourceBankId() {
        try {
            String sourceBankId = "22";
            Transaction actual = new Transaction(ID, ACCOUNT_ID, sourceBankId, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception -  invalid sourceBankId[wrong format]");
        } catch (IllegalArgumentException _) {}
    }

    @Test
    public void nullDate() {
        try {
            Transaction actual = new Transaction(ID, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, null, DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception - invalid date[null]");
        } catch (NullPointerException _) {}
    }

    @Test
    public void nullAmount() {
        try {
            Transaction actual = new Transaction(ID, ACCOUNT_ID, SOURCE_BANK_ID, null, DEFAULT_CURRENCY, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception - invalid amount[null]");
        } catch (NullPointerException _) {}
    }

    @Test
    public void nullCurrency() {
        try {
            Transaction actual = new Transaction(ID, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, null, LocalDateTime.now(), DEFAULT_DESCRIPTION );
            fail("Invalid transaction should throw an exception - invalid currency[null]");
        } catch (NullPointerException _) {}
    }

    @Test
    public void optionalDescription() {
        // let's say description is optional - no exception with null should be thrown
        try {
            String expectedDescription = "";
            Transaction actual = new Transaction(ID, ACCOUNT_ID, SOURCE_BANK_ID, AMOUNT, DEFAULT_CURRENCY, LocalDateTime.now(), null );

            assertNotNull(actual);
            assertEquals(expectedDescription, actual.getDescription());
        } catch (Exception _) {
            fail("Creating Transaction should not throw an exception - description[null]");
        }
    }
}