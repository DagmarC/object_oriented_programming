package com.cervenkova.model.transaction;

import com.cervenkova.model.enums.Category;
import com.cervenkova.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomingTransaction extends Transaction {

    public IncomingTransaction(long id,
                               LocalDateTime date,
                               BigDecimal amount,
                               Currency currency,
                               String accountId,
                               String sourceBankId,
                               String merchantName,
                               String message,
                               String type,
                               String description,
                               Category category) {

        super(id, date, requirePositive(amount), currency, accountId, sourceBankId, merchantName, message, type, description, category);
    }

    @Override
    public boolean isExpense() {
        return false;
    }

    @Override
    public String getSummary() {
        String status = isConfirmed() ? "confirmed" : "pending";
        return formatSummary("INCOME", status);
    }

    /**
     * The transaction is considered to be confirmed if the date of the transaction is not in the future.
     */
    public boolean isConfirmed() {
        return !getDate().isAfter(LocalDateTime.now());
    }
}
