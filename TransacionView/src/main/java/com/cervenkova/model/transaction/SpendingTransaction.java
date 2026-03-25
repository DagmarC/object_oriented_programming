package com.cervenkova.model.transaction;

import com.cervenkova.model.enums.Category;
import com.cervenkova.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SpendingTransaction extends Transaction {

    public SpendingTransaction(long id, LocalDateTime date, BigDecimal amount,
                               Currency currency, String accountId, String sourceBankId,
                               String merchantName, String message, String type, String description, Category category) {

        super(id, date, requireNegative(amount), currency, accountId, sourceBankId, merchantName, message, type, description, category);
    }

    @Override
    public boolean isExpense() {
        return true;
    }

    @Override
    public String getSummary() {
        return formatSummary("SPENT", "confirmed");
    }
}

