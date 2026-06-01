public interface RentalService {

    void addAvailableCar(Car car);

    void printAvailableCars();

    Result<String, CarRentalException> rentCar();

    Result<String, CarRentalException> returnCar(String SPZ, int km);
}
