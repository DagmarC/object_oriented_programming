public class Bishop extends Figure {

    public Bishop(Color color, Position position) {
        super(color, "bishop", position);
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        int targetX = newPosition.getX();
        int targetY = newPosition.getY();

        int y_difference = Math.abs(targetY - currentY);
        int x_difference = Math.abs(targetX - currentX);

        return y_difference == x_difference && y_difference != 0;
    }
}
