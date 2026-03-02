import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int SIZE = 8;

    private final Figure[][] board;

    public  Board() {
        this.board = new Figure[SIZE][SIZE];
    }

    public void addFigure(Figure figure) throws IllegalArgumentException, NullPointerException {
        if (figure == null) {
            throw new NullPointerException("Figure can't be null");
        }

        Position position = figure.getPosition();

        if (!validPosition(position)) {
            throw new IllegalArgumentException("Invalid position");
        }

        if (board[position.getX()][position.getY()] != null) {
            throw new IllegalArgumentException("Figure already exists at this position: " + position);
        }

        // Place figure on board
        board[position.getX()][position.getY()] = figure;
    }

    private boolean validPosition(Position position) {
        return position.getX() >= 0 && position.getX() < SIZE && position.getY() >= 0 && position.getY() < SIZE;
    }

    public List<Figure> getActiveFigures() {
        List<Figure> activeFigures = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != null && board[i][j].isActive()) {
                    Figure figure = board[i][j];
                    activeFigures.add(figure);
                }
            }
        }
        return activeFigures;
    }

    public void playTurn(Position from, Position to) {

    }
}
