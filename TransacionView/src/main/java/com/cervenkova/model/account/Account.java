package com.cervenkova.model.account;

import com.cervenkova.model.enums.Currency;
import com.cervenkova.model.transaction.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final String accountId;
    private final String bankId;
    private final String iban;
    private Currency currency;
    private BigDecimal balance;
    private List<Transaction> transactions;

    public Account(String accountId, String bankId, String iban, Currency currency, BigDecimal balance) {
        this.accountId = accountId;
        this.bankId = bankId;
        this.iban = iban;
        this.currency = currency;
        this.balance = balance;
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

    public BigDecimal totalSpending() {
        return transactions.stream()
                .filter(Transaction::isExpense)
                .map(t -> t.getAmount().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalIncome() {
        return transactions.stream()
                .filter(t -> !t.isExpense())
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBankId() {
        return bankId;
    }

    public String getIban() {
        return iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountId + "/" + bankId;
    }


}