public class King extends Figure {

    public King(Color color, Position position) {
        super(color, "king", position);
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        int newX = newPosition.getX();
        int newY = newPosition.getY();

        int xDiff = Math.abs(newX - currentX);
        int yDiff = Math.abs(newY - currentY);

        return !(xDiff == 0 && yDiff == 0) && (xDiff <= 1 && yDiff <= 1);
    }
}
