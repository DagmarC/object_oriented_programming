package com.cervenkova.model.account;

import java.util.List;
import java.util.Map;

public record BankApiResponse(
        Account account,
        List<Map<String, Object>> rawTransactions
) {}
