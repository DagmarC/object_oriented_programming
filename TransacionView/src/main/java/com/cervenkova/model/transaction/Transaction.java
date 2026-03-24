package com.cervenkova.model.transaction;

import com.cervenkova.model.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all bank transactions.
 * Subclasses define whether the transaction is an expense or income.
 */
public abstract class Transaction {

    // Mandatory transaction fields
    private final long id;
    private final LocalDateTime date;
    private final BigDecimal amount;
    private final Currency currency;

    // Optional transaction fields
    private final String sourceBankId;
    private final String description;
    private final String message;


    public Transaction(long id,
                       LocalDateTime date,
                       BigDecimal amount,
                       Currency currency,
                       String sourceBankId,
                       String description,
                       String message) {

        if (id <= 0)       throw new IllegalArgumentException("Id cannot be negative or 0.");
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
        if (amount == null)
            throw new IllegalArgumentException("Amount cannot be null");
        if (currency == null) throw new IllegalArgumentException("Currency cannot be null");

        this.id = id;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        // Optional
        this.sourceBankId = sourceBankId;
        this.description = description;
        this.message = message;

    }

    public abstract boolean isExpense();
    public abstract String getSummary();

    public long   getId()            { return id; }
    public BigDecimal getAmount()        { return amount; }
    public LocalDateTime getDate()   { return date; }
    public String getDescription()   { return description; }
    public Currency getCurrency() {return currency;}

    public String getSourceBankId() {return sourceBankId;}

    public String getMessage() {return message;}

    @Override
    public String toString() {
        return String.format("[%s] %-8s %8.2f %-4s | %s%s",
                date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                isExpense() ? "Expense" : "Income",
                amount.abs(),
                currency,
                description != null ? description : "-",
                message != null ? " (" + message + ")" : "");
    }

    protected static BigDecimal requirePositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount cannot be negative");
        return amount;
    }

    protected static BigDecimal requireNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0) throw new IllegalArgumentException("Amount cannot be positive");
        return amount;
    }

}
