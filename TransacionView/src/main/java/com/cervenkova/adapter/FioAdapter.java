package com.cervenkova.adapter;

import com.cervenkova.api.BankApiClient;
import com.cervenkova.model.account.BankApiResponse;
import com.cervenkova.model.transaction.Transaction;
import com.cervenkova.port.BankAccountPort;
import com.cervenkova.mapper.RowMapper;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// translation: maps Fio world to the App world, knows FioApiClient and what com.cervenkova.port.BankAccountPort requires
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
        // If the cache is empty or the date is different - GET new response
        if (cachedData == null || !from.equals(cachedFrom) || !to.equals(cachedTo)) {
            cachedData = fioApi.fetchAccountData(from, to);
            cachedFrom = from;
            cachedTo = to;
        }
        return cachedData;
    }


    @Override
    public List<Transaction> getTransactions(LocalDate from, LocalDate to) {
        FioAccount account = new FioAccount(); // composition
        for (Map<String, Object> row : getFioData(from, to).rawTransactions()) {
            account.addTransaction(rowMapper.map(row));
        }
        return account.getTransactions();
    }

    @Override
    public BigDecimal getBalance() {
        return getAccountData().accountInfo().balance();
    }

    @Override
    public String getAccountNumber() {
        return getAccountData().accountInfo().accountId() + "/" + cachedData.accountInfo().bankId();
    }

    @Override
    public String getBankId() {
        return getAccountData().accountInfo().bankId();
    }

    private BankApiResponse getAccountData() {
        return getFioData(
                LocalDate.now().minusDays(1),
                LocalDate.now()
        );
    }
}
