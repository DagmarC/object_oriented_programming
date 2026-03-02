public abstract class Figure implements MovementBehavior {

    private Color color;
    private String name;
    private Position position;
    private boolean active;

    public Figure(Color color, String name, Position position) {
        this.color = color;
        this.name = name;
        this.position = position;
        this.active = true;
    }

    @Override
    public void move(Position newPosition) throws NullPointerException, IllegalArgumentException {
        if (newPosition == null) {
            throw new NullPointerException("newPosition cant be null");
        }
        if (!isValidMove(newPosition)) {
            throw new IllegalArgumentException("Invalid move");
        }
        this.position = newPosition;
    }

    // This method will be implemented for each child separately
    @Override
    public abstract boolean isValidMove(Position newPosition);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
