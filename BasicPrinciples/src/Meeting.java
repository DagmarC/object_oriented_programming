public class Meeting {

    private String description;
    private Priority priority;

    public Meeting(String description, Priority priority) {
        setDescription(description);
        setPriority(priority);
    }

    public Meeting(String description) {
        this(description, Priority.LOW);
    }

    public Meeting() {
        this("Synchronization of people on certain task", Priority.LOW);
    }

    public Meeting(Priority priority) {
        this("Synchronization of people on certain task", priority);
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}