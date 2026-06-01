// =====================================================================
// SOLID – Single Responsibility Principle (SRP)
//   A dedicated exception class for domain errors keeps RuntimeException
//   sub-types meaningful. Callers can catch exactly this type instead of
//   a generic RuntimeException, making error handling precise and readable.
//
// CLEAN CODE – Name reveals intent: "CarRentalException" tells you
//   exactly which subsystem threw the error.
// =====================================================================
public class CarRentalException extends RuntimeException {

    // CLEAN CODE – Minimal class: one constructor, nothing extra.
    // KISS – Do not add fields/codes/causes unless actually needed.
    public CarRentalException(String message) {
        super(message);
    }
}
