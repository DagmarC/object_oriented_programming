// human-readable reporting class
public class SpendingTracker {

    private final BankAccountPort port;

    public SpendingTracker(BankAccountPort port) {
        this.port = port; // aggregation - port can exist without SpendingTracker
    }

    public void showReport() {
        System.out.println("SpendingTracker");
    }

    public double totalSpending() {
        // @TODO
        return 0;
    }

}
