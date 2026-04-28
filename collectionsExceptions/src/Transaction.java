import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

/**
 * For testing purposes. Tre transaction class from my previous impl was abstract class that could be either incoming or outcoming.
 */
public class Transaction {
    public static final String MSG_DEFAULT = "msg: default";

    // region Comparator static classes

    public static class ByAmountComparator implements Comparator<Transaction> {

        private final SortOrder sortOrder;
        public ByAmountComparator(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(Transaction t1, Transaction t2) {
            if (t1 == null || t2 == null)  throw new NullPointerException();

            int ret = Double.compare(t1.getAmount().doubleValue(), t2.getAmount().doubleValue());
            if (sortOrder == SortOrder.DESC) {
                ret = -ret;
            }
            return ret;
        }
    }

    public static class ByDateComparator implements Comparator<Transaction> {
        private final SortOrder sortOrder;

        public ByDateComparator(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(Transaction t1, Transaction t2) {
            if (t1 == null || t2 == null)  throw new NullPointerException();
            int ret = t1.getDate().compareTo(t2.getDate());
            if (sortOrder.equals(SortOrder.DESC)) {
                ret = -ret;
            }
            return ret;
        }
    }

    // endregion

    // region Transaction fields

    // Mandatory transaction fields
    private final long id;
    private String accountId;
    private String sourceBankId;
    private final BigDecimal amount;
    private final Currency currency;
    private final LocalDateTime date;

    // Optional transaction fields
    private final Category category;
    private final String description;
    private final String type;
    private final String message;
    private final String merchantName;

    // endregion

    // region Constructor

    public Transaction(long id, String accountId, String sourceBankId, BigDecimal amount, Category category,
                       Currency currency, LocalDateTime date, String description,
                       String type,
                       String message,
                       String merchantName) {

        setBankAccount(accountId, sourceBankId); // these params are not final, they require better validation for more comprehensive unit tests, so I want to use setter.

        // Mandatory fields
        if (id <= 0) throw new IllegalArgumentException("Id cannot be negative or 0.");
        if (amount == null) throw new NullPointerException("Amount cannot be null");
        if (currency == null) throw new NullPointerException("Currency cannot be null");
        if (date == null) throw new NullPointerException("Date cannot be null");

        // Optional fields
        if (category == null) category = Category.OTHER;
        if (description == null) description = "";
        if (type == null) type = "";
        if (message == null) message = "";
        if (merchantName == null) merchantName = "";

        this.id = id;
        this.amount = amount;
        this.category = category;
        this.currency = currency;
        this.date = date;
        this.description = description.trim();
        this.type = type;
        this.message = message.trim();
        this.merchantName = merchantName.trim();
    }

    public Transaction(long id, String accountId, String sourceBankId, BigDecimal amount,
                       Currency currency, LocalDateTime date, String description) {
        this(id, accountId, sourceBankId, amount, Category.OTHER, currency, date, description, "", MSG_DEFAULT, "");
    }

    // endregion

    // region Setters
    public void setBankAccount(String accountId, String sourceBankId) throws NumberFormatException, NullPointerException {
        if (accountId == null || accountId.isBlank()) throw new NullPointerException("Account ID cannot be null");
        if (sourceBankId == null || sourceBankId.isBlank()) throw new NullPointerException("Source Bank ID cannot be null");

        String trimmedAccountId = accountId.trim();
        if (!trimmedAccountId.matches("\\d{6}")) throw new IllegalArgumentException("Account ID is not a valid account ID. " +
                "It should contain 6 digits but it has: " + trimmedAccountId);

        String trimmedSourceBankId = sourceBankId.trim();
        if (!trimmedSourceBankId.matches("\\d{4}")) throw new IllegalArgumentException("Source bank ID is not valid. " +
                "It should contain 4 digits and it has: "+ trimmedSourceBankId);

        this.accountId = trimmedAccountId;
        this.sourceBankId = trimmedSourceBankId;
    }
    // endregion

    // region Getters

    public long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getSourceBankId() {
        return sourceBankId;
    }

    public String getMessage() {
        return message;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    // endregion

    @Override
    public String toString() {
        return String.format("T: %-6s/%4s | %8.2f %-4s | %-20s",
                getAccountId(), getSourceBankId(), getAmount(), getCurrency(), getDate()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Objects.equals(date, that.date) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, accountId);
    }
}