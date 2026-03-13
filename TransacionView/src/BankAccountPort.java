import java.util.*;


/**
 * Every new bank integration = one new adapter that implements this.
 * BankAccountPort is the most important class in the whole design — everything else revolves around it.
 * It is a contract. Nothing more. It says:
 * "whatever claims to be a bank account must be able to do these four things."
 * It contains zero logic, zero data, zero implementation. It is just a list of promises.
 */
public interface BankAccountPort {

    List<Transaction> getTransactions();

    double getBalance();

    String getAccountNumber();

    String getBankName();
}
