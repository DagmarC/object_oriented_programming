import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Transactions extends ArrayList<Transaction> {

    @Override
    public boolean add(Transaction t) {
        if (t == null) throw new NullPointerException();
        return super.add(t);
    }

    public List<Transaction> getByCurrency(Currency currency) throws IllegalArgumentException, NoSuchElementException {
        if (currency == null) throw new IllegalArgumentException("Currency cannot be null");
        if (this.isEmpty()) throw new NoSuchElementException("Transaction list is empty");

        return this.stream()
                .filter(t -> t.getCurrency().equals(currency)).toList();
    }

    public List<Transaction> getByAccountId(String accountId) throws IllegalArgumentException, NoSuchElementException {
        if (accountId == null) throw new IllegalArgumentException("Account ID cannot be null");
        if (accountId.isBlank()) throw new IllegalArgumentException("Account ID cannot be blank");
        if (this.isEmpty()) throw new NoSuchElementException("Transaction list is empty");

        return this.stream()
                .filter(t -> t.getAccountId().equalsIgnoreCase(accountId)).toList();
    }

    public List<Transaction> getMoreExpensiveThan(BigDecimal amount) throws IllegalArgumentException, NoSuchElementException {
        if (amount.doubleValue() < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (this.isEmpty()) throw new NoSuchElementException("Transaction list is empty");

        return this.stream().filter(t -> t.getAmount().compareTo(amount) > 0).toList();
    }


}
