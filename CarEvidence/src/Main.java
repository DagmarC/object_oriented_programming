//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Car car1 = new Car("123", 100);
    Car car2 = new Car("1232", 10);
    Car car3 = new Car("1234", 855);
    Car car4 = new Car("1233", 15);

    RentalService rentalService = new CarRentalService();
    rentalService.addAvailableCar(car1);
    rentalService.addAvailableCar(car2);
    rentalService.addAvailableCar(car3);
    rentalService.addAvailableCar(car4);

    rentalService.printAvailableCars();

    var SPZ = callRentalService(rentalService);
    var SPZ2 = callRentalService(rentalService);
    System.out.println(SPZ + ", " + SPZ2);

    rentalService.printAvailableCars();


    rentalService.returnCar(SPZ, 1000);
    rentalService.printAvailableCars();
    rentalService.returnCar(SPZ2, 20);
    rentalService.printAvailableCars();

}

private static String callRentalService(RentalService rentalService) {
    var result = rentalService.rentCar();

    return String.valueOf(switch (result) {
        case Success<String, CarRentalException> ok  -> ok.value();
        case Failure<String, CarRentalException> err -> {
            System.out.println("ERROR: " + err.err().getMessage());
            yield "NO-SPZ";
        }
    });
}
