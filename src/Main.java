import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            // Initialize new board with figures
            Board board = BoardSetup.initialize();

            System.out.println("----------- START GAME -----------\n");
            board.print2D();

            System.out.println("----------- WHITE TURN -----------");
            executeMove(board, new Position(2, 1), new Position(2, 3));

            System.out.println("----------- BLACK TURN -----------");
            executeMove(board, new Position(1, 7), new Position(2, 5));

            // Check invalid move - move the already moved black knight
            executeMove(board, new Position(1, 7), new Position(2, 5));

            // Check invalid move - move to correct place, but not empty (W-W)
            executeMove(board, new Position(2, 0), new Position(3, 1));

            // Move black knight
            System.out.println("----------- BLACK TURN -----------");
            executeMove(board, new Position(2, 5), new Position(3, 3));
            // Get The WHITE Pawn
            System.out.println("----------- BLACK TURN -----------");
            executeMove(board, new Position(3, 3), new Position(4, 1));

            List<Figure> activeFigures = board.getActiveFigures();

            System.out.println("--- Where are Black Knights? ---");
            activeFigures.stream()
                .filter(f -> f.getColor() == Color.BLACK && f.getName().equals("N"))
            .forEach(System.out::println);

        System.out.println("--- Count number of White Pawns? ---");

        long whitePawnsCnt = activeFigures.stream()
                    .filter(f -> f.getColor() == Color.WHITE && f.getName().equals("P"))
                    .count();
        System.out.println("Count White Pawns: " + whitePawnsCnt);
    }

    private static void executeMove(Board board, Position from, Position to) {
        try {
            board.playTurn(from, to);
            board.print2D();
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("❌ Invalid Move: " + e.getMessage() + "\n");
        }
    }
}