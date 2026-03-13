import java.util.*;

public class FioAccount {
    private List<Transaction> transactions;

    public FioAccount() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
