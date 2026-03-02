//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Meeting grooming = new Meeting("Grroming", Priority.HIGH);
        Task groomTask1 = new Task("Implement ...", Priority.MEDIUM);

        System.out.println(grooming);
        System.out.println(groomTask1);
    }
}