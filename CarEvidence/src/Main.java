// =====================================================================
// CLEAN CODE – Remove IDE-generated tip comments before sharing code.
//   Comments like "TIP: press Shift+F10 to run" are noise; they describe
//   the tool, not the program. Delete them.
//
// Java 21 "unnamed class" feature — void main() without a class wrapper
//   is valid for quick demos. Fine for school work; production code uses
//   a proper public class Main with public static void main(String[] args).
// =====================================================================

// =====================================================================
// CLEAN CODE – Demonstrate a complete, readable flow:
//   add cars → rent → return → rent → return → rent → return
//   Then show every error path so the reader sees the full behaviour.
// =====================================================================
void main() {

    RentalService service = new CarRentalService(); // DIP: depend on interface

    // --- Fleet setup ---
    service.addCar(new Car("1A2 3456", 15_000)); // underscores improve readability of large numbers
    service.addCar(new Car("2B3 4567", 8_200));
    service.addCar(new Car("3C4 5678", 22_500));

    System.out.println("=== Initial fleet ===");
    service.printAvailableCars();

    // --- Rental 1: expect 2B3 4567 (lowest km = 8 200) ---
    System.out.println("\n=== Rental 1 ===");
    String plate1 = rent(service);
    service.printAvailableCars();

    returnAndPrint(service, plate1, 340);
    service.printAvailableCars();

    // --- Rental 2: 2B3 4567 now has 8 540 — still lowest ---
    System.out.println("\n=== Rental 2 ===");
    String plate2 = rent(service);

    returnAndPrint(service, plate2, 6_000); // 2B3 4567 → 14 540; next lowest is 1A2 3456 (15 000)
    service.printAvailableCars();

    // --- Rental 3: 2B3 4567 has 14 540 < 15 000, so still picked ---
    System.out.println("\n=== Rental 3 ===");
    String plate3 = rent(service);

    returnAndPrint(service, plate3, 1_500);
    service.printAvailableCars();

    // --- Error handling: every Failure path ---
    System.out.println("\n=== Error scenarios ===");

    // 1. Return a car that was never rented
    returnAndPrint(service, "9Z9 9999", 100);

    // 2. Add a duplicate car
    tryAddDuplicate(service);

    // 3. Negative km on construction
    tryNegativeKmOnCreate();

    // 4. Rent when the fleet is empty
    tryRentEmptyFleet();
}

// =====================================================================
// CLEAN CODE / DRY – Each helper has one job and an intent-revealing name.
//   Duplicating the switch-on-Result block in main() would be a DRY
//   violation; extracting it here makes main() read like a story.
// =====================================================================

/** Rents a car, prints the result, and returns the plate (or "NO-PLATE" on failure). */
private static String rent(RentalService service) {
    Result<String, CarRentalException> result = service.rentCar();
    switch (result) {
        case Success<String, CarRentalException> ok  ->
                System.out.println("[RENT]    plate assigned: " + ok.value());
        case Failure<String, CarRentalException> err ->
                System.out.println("[ERROR]   " + err.err().getMessage());
    }
    return result.getOrElse("NO-PLATE");
}

/** Returns a car, prints the outcome. */
private static void returnAndPrint(RentalService service, String plate, int km) {
    Result<String, CarRentalException> result = service.returnCar(plate, km);
    switch (result) {
        case Success<String, CarRentalException> ok  ->
                System.out.printf("[RETURN]  plate: %s  km driven: %d%n", ok.value(), km);
        case Failure<String, CarRentalException> err ->
                System.out.println("[ERROR]   " + err.err().getMessage());
    }
}

private static void tryAddDuplicate(RentalService service) {
    try {
        service.addCar(new Car("1A2 3456", 0)); // already in fleet
    } catch (IllegalStateException e) {
        System.out.println("[ERROR]   " + e.getMessage());
    }
}

private static void tryNegativeKmOnCreate() {
    try {
        new Car("BAD 0001", -1);
    } catch (IllegalArgumentException e) {
        System.out.println("[ERROR]   " + e.getMessage());
    }
}

private static void tryRentEmptyFleet() {
    // CLEAN CODE – isolated sub-demo with its own service so the main
    // fleet is not polluted. Each test is self-contained (KISS).
    RentalService empty = new CarRentalService();
    empty.addCar(new Car("X1X 0001", 500));
    rent(empty); // succeeds
    rent(empty); // fails — no cars left
}
