public class Pawn extends Figure {

    public Pawn(Color color, Position position) {
        super(color, "pawn", position);
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        // White - moves alongside y-axis +1=UP && Black - moves alongside y-axis -1=DOWN
        Color color = this.getColor();

        // Starting y-axis position of White is 1
        int startWhiteY = 1;
        // Starting y-axis position of Black is 6
        int startBlackY = 6;

        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        int newX = newPosition.getX();
        int newY = newPosition.getY();

        // y-axis: White y+1, Black y-1
        int direction = color == Color.WHITE ? 1 : -1;

        boolean initPosition = color == Color.WHITE ? (currentY == startWhiteY) : (currentY == startBlackY);
        //Init position: can  move y+-2, x
        boolean initMove = initPosition && (newY - currentY == direction*2) && (newX - currentX == 0);

        // Either y+-1 and x+-+1 (when opposite figure is taken)
        return ((newY - currentY == direction) && Math.abs(newX - currentX) <= 1) || initMove;
    }
}
