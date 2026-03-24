package com.cervenkova.adapter;

import com.cervenkova.model.transaction.IncomingTransaction;
import com.cervenkova.model.transaction.Transaction;

import java.util.*;

// data class - store transactions
public class FioAccount {
    private final List<Transaction> transactions;

    public FioAccount() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) throws IllegalArgumentException {
        if (t == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

}
