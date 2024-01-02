package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece implements DiagonalMove, LinearMove{
    public Queen(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    @Override
    protected boolean isValidMove(Square target) {
        return (isOnline(this.getSquare(), target) || isOnDiagonal(this.getSquare(), target));
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }
}
