import java.time.LocalDateTime;

public class SpendingTransaction extends Transaction {

    private final String category;
    private final String merchantName;
    private final boolean isRecurring;

    public SpendingTransaction(long id, double amount, LocalDateTime date, String description, String category, String merchantName, boolean isRecurring) {
        super(id, amount, date, description);
        this.category = category;
        this.merchantName = merchantName;
        this.isRecurring = isRecurring;
    }

    @Override
    public boolean isExpense() {
        return false;
    }

    @Override
    public String getSummary() {
        return String.format("SPENT | %-14s | %-20s | %8.2f CZK%s",
                category, merchantName, Math.abs(getAmount()),
                isRecurring ? "  [recurring]" : "");
    }

    public String  getCategory()     { return category; }
    public String  getMerchantName() { return merchantName; }
    public boolean isRecurring()     { return isRecurring; }

}
