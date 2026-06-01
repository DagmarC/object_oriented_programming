import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CarRentalService implements RentalService {

    private final Map<Integer, Car> availableCars = new TreeMap<>();
    private final Map<String, Car> rentedCars = new HashMap<>();

    public void addAvailableCar(Car car) {
        Objects.requireNonNull(car);

        boolean duplicate = availableCars.values().stream().anyMatch(c -> c.equals(car));
        if (duplicate) {
            throw new IllegalStateException("Car already exists");
        }

        availableCars.put(car.km(), car);
    }

    public void printAvailableCars() {
        System.out.println("---------------------------");
        availableCars.values().forEach(System.out::println);
        System.out.println("---------------------------");
    }

    @Override
    public Result<String, CarRentalException> rentCar() {
        if (availableCars.isEmpty()) {
            return new Failure<>(new CarRentalException("No cars available"));
        };
        Car car = getCarForRent();
        return new Success<>(car.SPZ());
    }

    private Car getCarForRent() {
        Car car = availableCars.entrySet().stream().toList().getFirst().getValue();
        availableCars.remove(car.km(), car);
        rentedCars.put(car.SPZ(), car);
        return car;

    }

    @Override
    public Result<String, CarRentalException> returnCar(String SPZ, int km) {
        if (SPZ == null) {
            return new Failure<>(new CarRentalException("SPZ is null"));
        }
        if (SPZ.isBlank()) {
            return new Failure<>(new CarRentalException("SPZ is blank"));
        }
        if (km <= 0) {
            return new Failure<>(new CarRentalException("km is negative"));
        }

        if (!rentedCars.containsKey(SPZ)) {
            return new Failure<>(new CarRentalException("Car with SPZ: " + SPZ + " was not rented."));
        }

        Car car = rentedCars.get(SPZ);

        car.addKm(km);

        rentedCars.remove(SPZ);
        availableCars.put(car.km(), car);

        return new Success<>(car.SPZ());
    }
}
