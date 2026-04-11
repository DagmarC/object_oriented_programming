import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Account class needs to have at least 5 different types.
public class Account {

    private static final int MAX_5_DIGIT_N = 100000;
    private static final int MIN_5_DIGIT_N = 9999;

    private static final int MAX_4_DIGIT_N = 10000;
    private static final int MIN_4_DIGIT_N = 999;

    private static final String SK_IBAN_PATTERN = "^SK\\d{2}(\\d{4})$";
    private static final String CZ_IBAN_PATTERN = "^CZ\\d{2}(\\d{4})$";

    private int accountId;
    private int bankId;
    private String iban;
    private final Currency currency;
    private double balance;
    private final boolean active;

    private final List<Transaction> transactions;


    public Account(int accountId, int bankId, String iban, Currency currency, double balance, boolean active) {
        if (iban == null || iban.isBlank()) throw new IllegalArgumentException("Iban cannot be null.");
        if (currency == null) throw new IllegalArgumentException("Currency cannot be null.");

        setAccountId(accountId);
        setBankId(bankId);
        setIban(iban);

        this.currency = currency;
        this.balance = balance;
        this.active = active;

        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) throws IllegalArgumentException {
        if (transaction == null) throw new IllegalArgumentException("Transaction cannot be null.");
        transactions.add(transaction);
        // transaction amount is + if  it is incoming, otherwise it is negative
        this.balance += transaction.getAmount();
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        if (accountId <= MIN_5_DIGIT_N || accountId >= MAX_5_DIGIT_N) {
            throw new IllegalArgumentException("AccountId must contain 5 digits.");
        }
        this.accountId = accountId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        if (bankId <= MIN_4_DIGIT_N || bankId >= MAX_4_DIGIT_N) {
            throw new IllegalArgumentException("BankId must contain 4 digits.");
        }
        this.bankId = bankId;
    }

    public String getIban() {
        return iban;
    }

    // czech or slovak
    public void setIban(String iban) {
        if (iban == null) {
            throw new IllegalArgumentException("Iban cannot be null.");
        }
        // remove blank
        String cleanIban = iban.replaceAll("\\s+", "");

        if (!cleanIban.matches(SK_IBAN_PATTERN) && !cleanIban.matches(CZ_IBAN_PATTERN)) {
            throw new IllegalArgumentException("IBAN must be in the format SK|CZ and 6 digits, e.g. SK111111. It was: " + cleanIban);
        }
        this.iban = iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", bankId=" + bankId +
                ", iban='" + iban + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                ", active=" + active +
                ", transactions=" + transactions +
                '}';
    }
}