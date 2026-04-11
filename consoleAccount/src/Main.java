import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static Set<Integer> ids = new HashSet<>();

    public static void main(String[] args) {

        try {
            System.out.println("=== LOAD ACCOUNT ===");
            Account account = loadAccount();

            System.out.println("Account has been created.");
            System.out.println(account);

            System.out.println("=== LOAD TRANSACTION ===");

            Transaction transaction = loadTransaction();
            account.addTransaction(transaction);
            System.out.println("Transaction has been created and added to the account.");

            System.out.println(account);

        } catch (DuplicateTransactionException e) {
            System.err.println("Error DuplicateException: " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid input: " +e.getMessage());
        }
        catch (IOException e) {
            System.err.println("Error reading from stream input: " + e.getMessage());
        }
    }

    public static Account loadAccount() throws IllegalArgumentException, IOException {
        String accountIdStr = readInput("Enter account ID (5 digits): ");
        int accountId = parseMyInt(accountIdStr, "Account ID");

        String bankIdStr = readInput("Enter bank ID (4 digits): ");
        int bankId = parseMyInt(bankIdStr, "Bank ID");

        String iban = readInput("Enter IBAN (SK/CZ): ");

        String currencyStr = readInput("Enter currency code [CZK/EUR/USD]: ");
        Currency currency = Currency.fromString(currencyStr);

        String balanceStr = readInput("Enter balance: ");
        double balance = parseMyDouble(balanceStr, "Balance");

        String activeStr = readInput("Is active account [y/n]: ");
        boolean active = "y".equalsIgnoreCase(activeStr.trim());

        return new Account(accountId, bankId, iban, currency, balance, active);
    }

    private static Transaction loadTransaction() throws DuplicateTransactionException, IOException, IllegalArgumentException {
        String idStr = readInput("Enter transaction ID (unique), requires positive number");
        int id = parseMyInt(idStr, "Transaction ID");

        checkTransactionIdI(id);

        String incomingStr = readInput("Is it incoming transaction? [y/n]");
        boolean incoming = parseBoolean(incomingStr);

        String amountStr = readInput("Enter amount of transaction: ");
        double amount = parseMyDouble(amountStr, "Transaction amount");

        return new Transaction(id, incoming, amount);
    }

    public static String readInput(String prompt) throws IOException {
        String input;
        System.out.print(prompt);
        input = br.readLine();
        if (input == null) {
            throw new IOException("Input stream closed unexpectedly.");
        }
        return input;
    }

    private static void checkTransactionIdI(int id) throws DuplicateTransactionException {
        if (ids.contains(id)) {
            throw new DuplicateTransactionException("Transaction with ID " + id + " already exists.");
        }
    }

    // ======== PARSING ========

    private static double parseMyDouble(String input, String name) {
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for " + name + ": '" + input + "'. Must be number (double).");
        }
    }

    private static int parseMyInt(String input, String name) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for " + name + ": '" + input + "'. Must be integer.");
        }
    }

    private static boolean parseBoolean(String input) throws IllegalArgumentException {
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
            return true;
        } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
            return false;
        }
        throw new IllegalArgumentException("enter only y|n, entered: " + input);
    }

}