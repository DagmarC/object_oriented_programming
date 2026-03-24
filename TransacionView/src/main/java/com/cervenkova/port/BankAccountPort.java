package com.cervenkova.port;

import com.cervenkova.model.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


/**
 * Every new bank integration = one new adapter that implements this.
 * com.cervenkova.port.BankAccountPort is the most important class.
 * Contract: "whatever claims to be a bank account must be able to do these four things."
 * It contains zero logic, zero data, zero implementation. It is just a list of promises.
 */
public interface BankAccountPort {

    List<Transaction> getTransactions(LocalDate from, LocalDate to);

    BigDecimal getBalance();

    String getAccountNumber();

    String getBankId();
}
