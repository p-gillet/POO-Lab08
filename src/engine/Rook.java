package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece implements LinearMove {
    public Rook(Square square, PlayerColor color, Board board){
        super(color, square, board);
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isValidMove(Square dest) {
        return isOnline(this.getSquare(), dest);
    }

    public String textValue(){ return "Rook";}

    public String toString(){ return textValue();}
}
