import java.util.List;
import java.util.Map;

// translation: maps Fio world to the app world, knows FioApiClient and what BankAccountPort requires
public class FioAdapter implements BankAccountPort {

    private final FioApiClient fioApi;

    public FioAdapter(String token) {
        this.fioApi = new FioApiClient(token);
    }


    @Override
    public List<Transaction> getTransactions() {
        FioAccount account = new FioAccount();           // created here
        for (Map<String, Object> row : fioApi.fetchRawTransactions()) {
            account.addTransaction(parseRow(row));       // filled here
        }
        return account.getTransactions();                // returned here
    }

    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public String getAccountNumber() {
        return "";
    }

    @Override
    public String getBankName() {
        return "";
    }
}
