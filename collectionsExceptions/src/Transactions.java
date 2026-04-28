import java.math.BigDecimal;
import java.util.*;

public class Transactions extends ArrayList<Transaction> {

    private final Set<Long> ids = new HashSet<>();

    @Override
    public boolean add(Transaction t) {
        if (t == null) throw new NullPointerException();

        if (ids.contains(t.getId())) {
            throw new DuplicateTransactionException("Transaction with id " + t.getId() + " already exists");
        }
        ids.add(t.getId());

        return super.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Transaction t) ids.remove(t.getId());
        return super.remove(o);
    }

    @Override
    public void clear() {
        ids.clear();
        super.clear();
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
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        if (amount.doubleValue() < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (this.isEmpty()) throw new NoSuchElementException("Transaction list is empty");

        return this.stream().filter(t -> t.getAmount().compareTo(amount) > 0).toList();
    }

    public void sortByAmount(SortOrder order) throws NullPointerException {
        if (order == null) order = SortOrder.ASC;
        if (this.isEmpty()) return;

        this.sort(new Transaction.ByAmountComparator(order));
    }

    public void sortByDate(SortOrder order) throws NullPointerException {
        if (order == null) order = SortOrder.ASC;
        if (this.isEmpty()) return;

        this.sort(new Transaction.ByDateComparator(order));

    }
}
