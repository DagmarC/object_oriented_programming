public class Rook extends Figure {

    public Rook(Color color, Position position) {
        super(color, "rook", position);
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        int targetX = newPosition.getX();
        int targetY = newPosition.getY();

        boolean staysOnSameX = (currentX == targetX);
        boolean staysOnSameY = (currentY == targetY);
        boolean staysOnSameSpot = (staysOnSameX && staysOnSameY);

        return (staysOnSameX || staysOnSameY) && !staysOnSameSpot;
    }
}
