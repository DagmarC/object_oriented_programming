import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Task {
    private Priority priority;
    private String description;
    private Person contactPerson;
    private Set<Person> participants = new HashSet<>();

    public Task(String description, Priority priority) {
        setDescription(description);
        setPriority(priority);
    }

    public Task(String description, Priority priority, Person contactPerson) {
        this(description, priority);
        assignContactPerson(contactPerson);
    }

    public void assignContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson; // can be null 0...1
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    // Note: multiple participants can be added at once: addParticipants(p1, p2, , p_n) can be used here
    public void addParticipants(Person... persons) {
        if (persons == null) {
            throw new IllegalArgumentException("Persons array cannot be null");
        }
        Collections.addAll(participants, persons);
    }

    // Collection used to be able to call List as well as Set (to prevent duplicates)
    public void addParticipants(Collection<Person> personCollection) {
        if (personCollection == null) {
            throw new IllegalArgumentException("Person list cannot be null");
        }
        this.participants.addAll(personCollection);
    }

    public Set<Person> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "description='" + description + '\'' +
                ", priority=" + priority +
                ", participants count=" + participants.size() +
                '}';
    }
}
