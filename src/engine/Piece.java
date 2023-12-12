package engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    PieceType type;
    PlayerColor color;
    Square square;
    Piece(PieceType type, PlayerColor color, Square square){
        this.type = type;
        this.color = color;
        this.square = square;
    }
    abstract public boolean canMove(Square dest);
}
