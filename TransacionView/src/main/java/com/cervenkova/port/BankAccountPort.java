package com.cervenkova.port;

import com.cervenkova.model.account.Account;
import com.cervenkova.model.transaction.Transaction;

import java.time.LocalDate;
import java.util.*;


/**
 * Every new bank integration = one new adapter that implements this.
 * BankAccountPort is the most important class.
 * Contract: "whatever claims to be a bank accountInfo must be able to do these four things."
 */
public interface BankAccountPort {

    /**
     * Get complete bank account information and transactions at given period..
     * @return Account object
     */
    List<Transaction> getTransactions(LocalDate from, LocalDate to);
    Account getAccountInfo(LocalDate from, LocalDate to);
}
