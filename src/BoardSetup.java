public class BoardSetup {

    public static Board initialize() {
        Board board = new Board();

        // Place Pawns
        for (int x = 0; x < 8; x++) {
            board.addFigure(new Pawn(Color.WHITE, new Position(x, 1)));
            board.addFigure(new Pawn(Color.BLACK, new Position(x, 6)));
        }

        // Place WHITE figures
        board.addFigure(new Rook(Color.WHITE, new Position(0, 0)));
        board.addFigure(new Knight(Color.WHITE, new Position(1, 0)));
        board.addFigure(new Bishop(Color.WHITE, new Position(2, 0)));
        board.addFigure(new Queen(Color.WHITE, new Position(3, 0)));
        board.addFigure(new King(Color.WHITE, new Position(4, 0)));
        board.addFigure(new Bishop(Color.WHITE, new Position(5, 0)));
        board.addFigure(new Knight(Color.WHITE, new Position(6, 0)));
        board.addFigure(new Rook(Color.WHITE, new Position(7, 0)));

        // Place BLACK figures
        board.addFigure(new Rook(Color.BLACK, new Position(0, 7)));
        board.addFigure(new Knight(Color.BLACK, new Position(1, 7)));
        board.addFigure(new Bishop(Color.BLACK, new Position(2, 7)));
        board.addFigure(new Queen(Color.BLACK, new Position(3, 7)));
        board.addFigure(new King(Color.BLACK, new Position(4, 7)));
        board.addFigure(new Bishop(Color.BLACK, new Position(5, 7)));
        board.addFigure(new Knight(Color.BLACK, new Position(6, 7)));
        board.addFigure(new Rook(Color.BLACK, new Position(7, 7)));

        return board;
    }
}