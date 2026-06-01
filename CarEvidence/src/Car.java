import java.util.Objects;

// =====================================================================
// CLEAN CODE – Meaningful names
//   Field "spz" is a Czech abbreviation. The task requires English only.
//   Renamed to "licensePlate" so any reader understands it immediately
//   without knowing Czech. (same applies to the accessor below)
//
// SOLID – Single Responsibility Principle (SRP)
//   Car is a pure data-holder: it stores state and validates its own
//   invariants. It has no knowledge of the rental process. Good.
// =====================================================================
public class Car {

    private final String licensePlate; // CLEAN CODE – English, self-documenting name
    private int km;

    public Car(String licensePlate, int km) {
        // CLEAN CODE – Guard clauses at the top; fail fast with a clear message.
        // Objects.requireNonNull is idiomatic and DRY (no hand-written null-check).
        this.licensePlate = Objects.requireNonNull(licensePlate, "licensePlate must not be null");

        // CLEAN CODE – Blank check in addition to null: "  " is not a valid plate.
        if (licensePlate.isBlank()) throw new IllegalArgumentException("licensePlate must not be blank");

        if (km < 0) throw new IllegalArgumentException("km cannot be negative");
        this.km = km;
    }

    public void addKm(int km) {
        if (km < 0) throw new IllegalArgumentException("cannot add negative km");
        this.km += km;
    }

    // CLEAN CODE – Java accessor convention: getXxx(), not just xxx() or XXX().
    // "SPZ()" violated two rules: it was not English and used uppercase,
    // breaking standard Java naming (methods start with a lowercase letter).
    public String getLicensePlate() { return licensePlate; }
    public int getKm()              { return km; }

    // =====================================================================
    // CLEAN CODE / SOLID – equals & hashCode
    //   Two Car objects represent the same physical car when their license
    //   plates match. Including "km" in equality is WRONG: the same car
    //   before and after a rental would be considered a different object,
    //   which would silently break HashMap/HashSet lookups.
    //   → Equality is based on licensePlate ONLY.
    // =====================================================================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // CLEAN CODE – short-circuit for same reference
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(licensePlate, car.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate); // consistent with equals
    }

    @Override
    public String toString() {
        // CLEAN CODE – use String.format / text blocks for readability
        return String.format("Car[plate=%s, km=%d]", licensePlate, km);
    }
}
