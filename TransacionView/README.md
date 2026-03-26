# TransactionView

Java application for tracking bank transactions via Fio Bank API.

## Project Structure
```
src/main/java/com/cervenkova/
├── Main.java                          # Entry point, wiring of all components
├── adapter/
│   └── FioAdapter.java                # Translates Fio API world into application world, caches responses
├── api/
│   ├── BankApiClient.java             # Interface for any bank API client
│   └── FioApiClient.java              # HTTP client for Fio REST API, parses JSON response
├── mapper/
│   └── RowMapper.java                 # Functional interface for mapping raw Map rows to Transaction objects
├── model/
│   ├── account/
│   │   ├── Account.java               # Domain object representing a bank account with transactions
│   │   └── BankApiResponse.java       # Record wrapping raw API response (Account + raw transactions)
│   ├── enums/
│   │   ├── Category.java              # Spending categories (GROCERIES, LOANS, SUBSCRIPTIONS, ...)
│   │   └── Currency.java              # Supported currencies (CZK, EUR, USD, UNKNOWN)
│   └── transaction/
│       ├── Transaction.java           # Abstract base class for all transactions
│       ├── IncomingTransaction.java   # Incoming payment, always confirmed (Fio returns only settled transactions)
│       └── SpendingTransaction.java   # Outgoing payment with category
├── port/
│   └── BankAccountPort.java           # Port interface — contract for any bank integration
└── tracker/
    └── SpendingTracker.java           # Business logic — human-readable report

test/java/com/cervenkova/
├── api/
│   ├── MockFioApiClient.java          # Mock client reading from local fio_response.json
│   └── MockFioApiClientTest.java      # Tests verifying correct parsing of Fio JSON response
└── model/
    └── AccountTest.java               # Tests verifying Account domain logic (transactions, totals)

test/resources/
└── fio_response.json                  # Sample Fio API JSON response used in MockFioApiClientTest
```

## Tests

### `MockFioApiClientTest`
Verifies that `MockFioApiClient` correctly parses the Fio JSON response from `fio_response.json`.
Tests cover:
- Account info (accountId, bankId, currency, balance, IBAN)
- Transaction list (id, amount, currency, merchantName, message, type, description, date)

### `AccountTest`
Verifies domain logic of the `Account` class.
Tests cover:
- Constructor validation (null checks for mandatory fields)
- Adding transactions
- Unmodifiable transaction list
- `totalSpending()` and `totalIncome()` calculations

## Running Tests
```bash
mvn test
```

## Configuration

Fio API token is loaded from environment variable:
```bash
export FIO_TOKEN=your-token-here
mvn test
```