public interface MovementBehavior {

     void move(Position newPosition);

     boolean isValidMove(Position newPosition);
}
