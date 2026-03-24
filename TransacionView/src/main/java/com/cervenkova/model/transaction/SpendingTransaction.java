package com.cervenkova.model.transaction;

import com.cervenkova.model.enums.Category;
import com.cervenkova.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SpendingTransaction extends Transaction {

    private final Category category;
    private final String merchantName; // column_10 — názov protistrany - Optional;
    private static final String UNKNOWN = "Unknown";

    public SpendingTransaction(long id,
                               LocalDateTime date,
                               BigDecimal amount,
                               Currency currency,
                               String sourceBankId,
                               String description,
                               String message,
                               Category category,
                               String merchantName) {

        super(id, date, requireNegative(amount), currency, sourceBankId, description, message);

        this.category = category != null ? category : Category.OTHER;
        this.merchantName = (merchantName != null && !merchantName.isBlank()) ? merchantName:  UNKNOWN;
    }

    @Override
    public boolean isExpense() {
        return true;
    }

    @Override
    public String getSummary() {
        return String.format("SPENT | %-14s | %-20s | %8.2f %-4s",
                category, merchantName, getAmount().abs(), getCurrency());
    }

    public Category getCategory()     { return category; }
    public String getMerchantName() { return merchantName; }
}

