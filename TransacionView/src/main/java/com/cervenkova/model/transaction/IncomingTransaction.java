package com.cervenkova.model.transaction;

import com.cervenkova.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomingTransaction extends Transaction {

    public IncomingTransaction(long id,
                               LocalDateTime date,
                               BigDecimal amount,
                               Currency currency,
                               String source,
                               String description,
                               String message) {
        super(id, date, requirePositive(amount), currency, source, description, message);
    }

    @Override
    public boolean isExpense() {
        return false;
    }

    @Override
    public String getSummary() {
        String status = isConfirmed() ? "confirmed" : "pending";

        return String.format("INCOME | %-20s | %8.2f %-4s | %-20s | %s",
                getSourceBankId(), getAmount(), getCurrency(), getDescription(), status);
    }

    /**
     * The transaction is considered to be confirmed if the date of the transaction is not in the future.
     * */
    public boolean isConfirmed() {
        return !getDate().isAfter(LocalDateTime.now());
    }
}
