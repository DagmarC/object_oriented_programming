import java.time.LocalDateTime;

/**
 * Abstract base class for all bank transactions.
 * Subclasses define whether the transaction is an expense or income.
 */
public abstract class Transaction {
    private final long id;
    private final double amount; // +-
    private final LocalDateTime date;
    private final String description;

    public Transaction(long id, double amount, LocalDateTime date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public abstract boolean isExpense();
    public abstract String  getSummary();

    public long   getId()            { return id; }
    public double getAmount()        { return amount; }
    public LocalDateTime getDate()   { return date; }
    public String getDescription()   { return description; }

    @Override
    public String toString() {
        return String.format("[%s] %-8s %8.2f CZK  %s",
                date, isExpense() ? "Expense" : "Income", Math.abs(amount), description);
    }
}
