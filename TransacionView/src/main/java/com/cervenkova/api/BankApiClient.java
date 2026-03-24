package com.cervenkova.api;

import com.cervenkova.model.account.BankApiResponse;

import java.time.LocalDate;

public interface BankApiClient {
    BankApiResponse fetchAccountData(LocalDate from, LocalDate to);
}
