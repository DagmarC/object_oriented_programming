import java.time.LocalDateTime;

public class IncomingTransaction extends Transaction {

    private final String source;
    private final boolean isConfirmed;

    public IncomingTransaction(long id, double amount, LocalDateTime date, String description, String source, boolean isConfirmed) {
        super(id, Math.abs(amount), date, description);
        this.source = source;
        this.isConfirmed = isConfirmed;
    }

    @Override
    public boolean isExpense() {
        return false;
    }

    @Override
    public String getSummary() {
        String status = isConfirmed ? "done" : "waiting for confirmation";
        return String.format("INCOME | %-20s | %8.2f CZK | %s",
                source, getAmount(), status);    }


    public double getNetAmount() { return isConfirmed ? getAmount() : 0.0; }


    public String getSource() {
        return source;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
