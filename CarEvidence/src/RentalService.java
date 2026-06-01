// =====================================================================
// SOLID – Dependency Inversion Principle (DIP)
//   Main and any future caller depend on this abstraction, NOT on the
//   concrete CarRentalService. Swapping implementations (e.g. a
//   database-backed service) requires zero changes at the call site.
//
// SOLID – Interface Segregation Principle (ISP)
//   The interface is small and cohesive — every method belongs to the
//   concept of "renting cars". If printing logic grew complex it should
//   move to a separate Reporter interface (SRP).
// =====================================================================
public interface RentalService {

    // CLEAN CODE – Method names are verbs that describe an action clearly.
    void addCar(Car car);

    // NOTE: printAvailableCars() mixes presentation with business logic
    // (SRP violation). For a school demo it is acceptable, but in production
    // this would be replaced by a getCars() query + a separate Printer/View.
    void printAvailableCars();

    Result<String, CarRentalException> rentCar();

    // CLEAN CODE – Parameter name "licensePlate" is English and descriptive.
    // The original "SPZ" was Czech and violated Java's camelCase convention.
    Result<String, CarRentalException> returnCar(String licensePlate, int kmDriven);
}
