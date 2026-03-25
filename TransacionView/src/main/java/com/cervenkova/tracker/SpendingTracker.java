package com.cervenkova.tracker;

import com.cervenkova.model.account.Account;
import com.cervenkova.model.transaction.Transaction;
import com.cervenkova.port.BankAccountPort;

import java.time.LocalDate;
import java.util.List;

/**
 * SpendingTracker is responsible for business logic (human-readable report),
 *
 */
public class SpendingTracker {

    private final BankAccountPort port;
    private final LocalDate from;
    private final LocalDate to;

    public SpendingTracker(BankAccountPort port, LocalDate from, LocalDate to) {
        this.port = port;
        this.from = from;
        this.to = to;
    }

    public void showReport() {
        Account account = port.getAccountInfo(from, to);

        System.out.println("=== REPORT: " + account.getAccountNumber() + " ===");
        System.out.println("Date: " + from + " - " + to);
        System.out.println("Balance: " + account.getBalance() + " " + account.getCurrency());
        System.out.println("Total spent: " + account.totalSpending());
        System.out.println("Total income: " + account.totalIncome());

        List<Transaction> transactions = account.getTransactions();
        System.out.println("Total transactions: " + transactions.size());
        transactions.forEach(t -> System.out.println(t.getSummary()));
    }
}
