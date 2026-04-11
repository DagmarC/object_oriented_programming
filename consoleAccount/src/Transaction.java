// Simplified version of transaction
public class Transaction {
    private int id;
    private boolean incoming;
    private double amount;


    public Transaction(int id, boolean incoming, double amount) {
        setId(id);
        setAmountAndIncoming(amount, incoming);
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than zero");
        }
        this.id = id;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    public void setAmountAndIncoming(double amount, boolean incoming) {
        if (incoming && amount <= 0) {
            throw new IllegalArgumentException("Error: It is incoming transaction, so the amount must be greater than zero. Got: " + amount);
        }
        if (!incoming && amount > 0) {
            throw new IllegalArgumentException("Error: It is outgoing transaction, so the amount must be less than or equal to zero. Got: " + amount);
        }
        this.incoming = incoming;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "\tTransaction: ID: " + id + "\n" +
                "\tIncoming: " + incoming + "\n" +
                "\tAmount: " + amount + "\n";
    }
}
