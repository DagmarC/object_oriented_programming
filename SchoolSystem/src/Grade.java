import java.time.LocalDateTime;

public record Grade(int value, LocalDateTime dateInserted, Lecturer lecturer) {

    public Grade {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Invalid value " + value + " for Grade. Allowed values are in range 1-5.");
        }
        if (lecturer == null) {
            throw new IllegalArgumentException("Lecturer cannot be null.");
        }
    }

    public Grade(int value, Lecturer lecturer) {
        this(value, LocalDateTime.now(), lecturer);
    }
}