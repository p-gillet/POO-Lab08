package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece implements DiagonalMove, LinearMove{
    public Queen(Square square, PlayerColor color, Board board){
        super(color, square, board);
    }

    @Override
    protected boolean isValidMove(Square target) {
        return (isOnLine(this.getSquare(), target) || isOnDiagonal(this.getSquare(), target));
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    public String textValue(){ return "Queen";}

    public String toString(){ return textValue();}
}
