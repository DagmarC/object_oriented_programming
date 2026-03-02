import java.util.Objects;

public class Person {

    private String name;
    private String phoneNumber;
    private String email;

    private static final String PHONE_REGEX = "^\\+420 \\d{3} \\d{3} \\d{3}$";

    public Person(String name, String phoneNumber, String email) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public Person(String email, String name) {
        setName(name);
        setPhoneNumber(""); // Phone number is optional here
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }

        // Empty string: accept it as "No phone number provided"
        if (phoneNumber.trim().isEmpty()) {
            this.phoneNumber = "";
            return;
        }

        // It MUST match the regex format
        if (!phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number format. Must match: +420 XXX XXX XXX");
        }

        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}