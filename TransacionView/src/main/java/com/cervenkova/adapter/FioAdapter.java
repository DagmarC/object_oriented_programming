package com.cervenkova.adapter;

import com.cervenkova.api.BankApiClient;
import com.cervenkova.model.account.Account;
import com.cervenkova.model.account.BankApiResponse;
import com.cervenkova.model.transaction.Transaction;
import com.cervenkova.port.BankAccountPort;
import com.cervenkova.mapper.RowMapper;

import java.time.LocalDate;
import java.util.List;


/**
 * Maps Fio world into the Application world, knows FioApiClient and what BankAccountPort requires.
 * It also caches response from Fio to prevent repeating GET requests (by date period).
 * */
public class FioAdapter implements BankAccountPort {

    private final BankApiClient fioApi;
    private final RowMapper<Transaction> rowMapper;

    private BankApiResponse cachedData;
    private LocalDate cachedFrom;
    private LocalDate cachedTo;

    public FioAdapter(BankApiClient fioApi, RowMapper<Transaction> rowMapper) {
        this.fioApi = fioApi;
        this.rowMapper = rowMapper;
    }

    private BankApiResponse getFioData(LocalDate from, LocalDate to) {
        if (cachedData == null || !from.equals(cachedFrom) || !to.equals(cachedTo)) {
            cachedData = fioApi.fetchAccountData(from, to);
            cachedFrom = from;
            cachedTo = to;
        }
        return cachedData;
    }


    @Override
    public List<Transaction> getTransactions(LocalDate from, LocalDate to) {
        return getFioData(from, to).rawTransactions()
                .stream()
                .map(rowMapper::map)
                .toList();
    }

    @Override
    public Account getAccountInfo(LocalDate from, LocalDate to) {
        BankApiResponse apiResponse = getFioData(from, to);
        return apiResponse.account();
    }
}
