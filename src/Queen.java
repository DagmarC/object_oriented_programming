public class Queen extends Figure {

    private final Rook rook;
    private final Bishop bishop;

    public Queen(Color color, Position position) {
        super(color, "Q", position);

        // DRY principle: for movement validation
        bishop = new Bishop(Color.WHITE, this.getPosition());
        rook = new Rook(Color.BLACK, this.getPosition());
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        // Queen is the combination of bishop OR rook valid moves
        return bishop.isValidMove(newPosition) || rook.isValidMove(newPosition);
    }
}
