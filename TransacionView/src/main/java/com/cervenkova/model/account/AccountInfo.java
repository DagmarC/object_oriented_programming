package com.cervenkova.model.account;

import com.cervenkova.model.enums.Currency;

import java.math.BigDecimal;

public record AccountInfo(
        String accountId,
        String bankId,
        String iban,
        Currency currency,
        BigDecimal balance
) {}