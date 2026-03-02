public class Task {
    private Priority priority;
    private String description;
    private Person contactPerson;


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

    @Override
    public String toString() {
        return "Task{" +
                "priority=" + priority +
                ", description='" + description + '\'' +
                ", contactPerson=" + contactPerson +
                '}';
    }
}
