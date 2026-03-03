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

    public void playTurn(Position from, Position to) throws IllegalArgumentException, NullPointerException {
        int currentX = from.getX();
        int currentY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        Figure figure = this.board[currentX][currentY];
        if (figure == null) {
            throw new IllegalArgumentException("Invalid PlayTurn: No figure at this position" + from);
        }

        Color color = figure.getColor();

        // Investigate the destination position
        Figure nextFigure = this.board[nextX][nextY];
        if (nextFigure != null && nextFigure.getColor() == color) {
            throw new IllegalArgumentException("Invalid PlayTurn: Figure with the same color exists at destination position" + to);
        }

        // At this point I know the figure is valid and its move is validated inside the move() method
        figure.move(to);
        if (nextFigure != null) {
            nextFigure.setActive(false); // Opponent figure is lost
        }

        // Update board
        this.board[nextX][nextY] = figure;
        this.board[currentX][currentY] = null;
    }

    public void print2D() {
        System.out.println("---------------------------------------------------------");

        for (int y = 7; y >= 0; y--) {
            // Rows (7-0)
            System.out.print((y + 1) + " | ");

            // Cols (0-7)
            for (int x = 0; x < 8; x++) {
                Figure figure = this.board[x][y];

                if (figure == null) {
                    System.out.printf("%-7s", " . "); // empty
                } else {
                    String name = figure.getName();
                    String prefix = figure.getColor() == Color.WHITE ? "W" : "B";
                    System.out.printf("%-7s", prefix + name);
                }
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("     A      B      C      D      E      F      G      H  ");
    }
}











