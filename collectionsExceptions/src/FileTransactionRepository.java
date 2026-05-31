import java.io.*;

public class TransactionManager {
    private final Transactions transactions;

    public TransactionManager(Transactions transactions) {
        this.transactions = transactions;
    }

    public void serialize(String filename) throws RuntimeException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this.transactions);
        } catch (IOException e) {
            throw new RuntimeException("Error: while working with a file ", e);
        }
    }

    public Transactions deserialize(String filename) throws RuntimeException {
        Transactions result;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            result = (Transactions) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
