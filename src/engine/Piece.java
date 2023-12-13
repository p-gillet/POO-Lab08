package engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    PlayerColor color;
    Square square;
    Board board;

    Piece(PlayerColor color, Square square, Board board){
        this.color = color;
        this.square = square;
        this.board = board;
    }

    public abstract boolean canMove(Square dest);

    public void setSquare(Square square) {
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public abstract PieceType getType();

    public PlayerColor getColor() {
        return color;
    }
}
