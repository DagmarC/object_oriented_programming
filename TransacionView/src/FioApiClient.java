import java.util.*;
import java.util.Map;

// HTTP / data access, talks to Fio API (URLs, tokens, raw data)
public class FioApiClient {
    private final String token;

    public FioApiClient(String token) {
        this.token = token;
    }

    public List<Map<String, Object>> fetchRawTransactions() {
        // @TODO Specific RAW Fio api response
        return null;
    }
}
