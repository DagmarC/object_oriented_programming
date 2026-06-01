import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// =====================================================================
// SOLID – Single Responsibility Principle (SRP)
//   This class manages one thing: the lifecycle of car rentals.
//   It does not format output (beyond a simple print helper), parse
//   input, or own persistence. Each private method has one clear job.
//
// SOLID – Liskov Substitution Principle (LSP)
//   This class honours the RentalService contract fully:
//   every method behaves as the interface promises.
// =====================================================================
public class CarRentalService implements RentalService {

    // =====================================================================
    // BUG FIX – Original used TreeMap<Integer, Car> with km as the key.
    //   Problem: if two cars have identical km, the second put() silently
    //   overwrites the first — a car disappears from the fleet with no error.
    //
    // KISS / correctness fix:
    //   Use a plain List for the fleet (easy to reason about) and sort only
    //   when selecting the next car for rental. No tricky key collisions.
    // =====================================================================
    private final List<Car> availableCars = new java.util.ArrayList<>();
    private final Map<String, Car> rentedCars = new HashMap<>(); // keyed by licensePlate

    @Override
    public void addCar(Car car) {
        // CLEAN CODE – Guard clause first; keep the happy path at the bottom.
        Objects.requireNonNull(car, "car must not be null");

        // DRY – reuse Car.equals (which is now correctly plate-based) instead
        // of duplicating the comparison logic here.
        if (availableCars.contains(car)) {
            throw new IllegalStateException("Car already exists: " + car.getLicensePlate());
        }

        availableCars.add(car);
    }

    @Override
    public void printAvailableCars() {
        System.out.println("---------------------------");
        // Streams + lambda: sort for readable output, then print each car.
        availableCars.stream()
                .sorted(Comparator.comparingInt(Car::getKm))
                .forEach(System.out::println);
        System.out.println("---------------------------");
    }

    @Override
    public Result<String, CarRentalException> rentCar() {
        if (availableCars.isEmpty()) {
            // CLEAN CODE – Return Failure instead of throwing; the caller
            // decides how to handle the absence of a car.
            return new Failure<>(new CarRentalException("No cars available"));
        }
        Car car = pickCheapestCar();
        return new Success<>(car.getLicensePlate());
    }

    // =====================================================================
    // CLEAN CODE – Extracted private method with an intent-revealing name.
    //   "getCarForRent" in the original was vague. "pickCheapestCar" tells
    //   you the selection policy without reading the body.
    //
    // KISS – stream().min() expresses the selection rule in one line.
    //   Original: stream().toList().getFirst() — needlessly materialised
    //   the full list before picking the first element.
    // =====================================================================
    private Car pickCheapestCar() {
        // min() on a non-empty list is guaranteed to return a value,
        // so orElseThrow() here is a safety net, not normal flow.
        Car car = availableCars.stream()
                .min(Comparator.comparingInt(Car::getKm))
                .orElseThrow();

        availableCars.remove(car);
        rentedCars.put(car.getLicensePlate(), car);
        return car;
    }

    @Override
    public Result<String, CarRentalException> returnCar(String licensePlate, int kmDriven) {
        // =====================================================================
        // CLEAN CODE – Validate parameters at the entry point (guard clauses).
        //   Each validation returns immediately with a descriptive Failure.
        //   No nested ifs; the happy path is at the bottom — easy to read.
        // =====================================================================
        if (licensePlate == null || licensePlate.isBlank()) {
            return new Failure<>(new CarRentalException("licensePlate must not be blank"));
        }

        // =====================================================================
        // BUG FIX – Original condition was km <= 0, but the error message said
        //   "km is negative". These two were contradictory: returning a car with
        //   0 km driven is valid (e.g. car broke down immediately).
        //   Fixed: reject only truly negative values.
        // =====================================================================
        if (kmDriven < 0) {
            return new Failure<>(new CarRentalException("kmDriven cannot be negative"));
        }

        if (!rentedCars.containsKey(licensePlate)) {
            return new Failure<>(new CarRentalException(
                    "Car with plate " + licensePlate + " is not currently rented"));
        }

        Car car = rentedCars.remove(licensePlate);
        car.addKm(kmDriven);
        availableCars.add(car);

        return new Success<>(car.getLicensePlate());
    }
}
