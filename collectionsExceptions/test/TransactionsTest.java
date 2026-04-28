//import org.junit.Before;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//
//import static org.junit.Assert.*;
//
//public class TransactionsTest {
//
//    Transactions transactions = new Transactions();
//
//    @Before
//    public void setUp() {
//        // Testing data preparation
//
//        Transaction t1 = new Transaction(BigDecimal.valueOf(200), Category.INCOME, 1, "12340", Currency.EUR, "1st transaction");
//        Transaction t2 = new Transaction(BigDecimal.valueOf(5), Category.DRUG_STORE, 2, "12341", Currency.EUR, "2nd transaction");
//        Transaction t3 = new Transaction(BigDecimal.valueOf(-100), Category.INCOME, 3, "12340", Currency.CZK, "3rd transaction");
//        Transaction t4 = new Transaction(BigDecimal.valueOf(30), Category.DRUG_STORE, 4, "12342", Currency.EUR, "4th transaction");
//        Transaction t5 = new Transaction(BigDecimal.valueOf(-300), Category.CLOTHES, 5, "12343", Currency.CZK, "5yh transaction");
//
//        transactions.add(t1);
//        transactions.add(t2);
//        transactions.add(t3);
//        transactions.add(t4);
//        transactions.add(t5);
//
//    }
//
//    @Test
//    public void add() {
//        int oldSize = transactions.size();
//
//        Transaction expected = new Transaction(BigDecimal.valueOf(-250), Category.GARDEN_STUFF, 6, "12343", Currency.CZK, "6th transaction");
//
//        transactions.add(expected);
//
//        int currentSize = transactions.size();
//        assertEquals(oldSize + 1, currentSize);
//
//        Transaction actual = transactions.getLast();
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void addNullTransaction() {
//        try {
//            transactions.add(null);
//            fail("Null transaction should throw an exception");
//        } catch (NullPointerException _) {
//        }
//    }
//
//
//    @Test
//    public void getByCurrency() {
//    }
//
//    @Test
//    public void getByAccountId() {
//    }
//
//    @Test
//    public void getMoreExpensiveThan() {
//    }
//}