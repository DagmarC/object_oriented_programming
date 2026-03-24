package com.cervenkova.tracker;

import com.cervenkova.model.transaction.IncomingTransaction;
import com.cervenkova.model.transaction.Transaction;
import com.cervenkova.port.BankAccountPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// human-readable reporting class, business logic
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
        List<Transaction> transactions = port.getTransactions(from, to);
        transactions.forEach(t -> System.out.println(t.getSummary()));
    }

    public BigDecimal totalSpending() {
        return port.getTransactions(from, to).stream()
                .filter(Transaction::isExpense)
                .map(t -> t.getAmount().abs())
                // add BigDecimal objects
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalIncome() {
        return port.getTransactions(from, to).stream()
                .filter(t -> !t.isExpense())
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> confirmedTransactions() {
        return port.getTransactions(from, to).stream()
                .filter(t -> t instanceof IncomingTransaction it && it.isConfirmed())
                .toList();
    }

    public List<Transaction> pendingTransactions() {
        return port.getTransactions(from, to).stream()
                .filter(t -> t instanceof IncomingTransaction it && !it.isConfirmed())
                .toList();
    }

}
