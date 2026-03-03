public class Knight extends Figure{

    public Knight(Color color, Position position) {
        super(color, "N", position);
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        int newX = newPosition.getX();
        int newY = newPosition.getY();

        int xDiff = Math.abs(newX - currentX);
        int yDiff = Math.abs(newY - currentY);

        return (xDiff == 1 && yDiff == 2) || (xDiff == 2 && yDiff == 1);
    }
}
