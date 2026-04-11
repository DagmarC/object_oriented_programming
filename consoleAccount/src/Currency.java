import java.util.Optional;

public enum Currency {
    CZK,
    EUR,
    USD;

    public static Currency fromString(String txt) {
        if (txt == null || txt.isBlank()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty.");
        }

        String normalized = txt.toUpperCase().trim();

        try {
            return Currency.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid currency: '%s'. Supported values are: %s",
                            txt, java.util.Arrays.toString(Currency.values()))
            );
        }
    }
}
